package org.mjaworski.backend.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mjaworski.backend.converter.UserConverter;
import org.mjaworski.backend.dto.role.RoleDto;
import org.mjaworski.backend.dto.user.UserDto;
import org.mjaworski.backend.exception.forbidden.UserBlockedException;
import org.mjaworski.backend.exception.unauthorized.UserNotFoundException;
import org.mjaworski.backend.persistance.entity.Role;
import org.mjaworski.backend.persistance.entity.User;
import org.mjaworski.backend.persistance.repository.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TokenAuthentication {
    private String secret;
    private int expirationTime;
    private int refreshAfter;

    static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_NAME = "Authorization";
    public static final String X_USER_HEADER_NAME = "X-User";

    private UserRepository userRepository;
    private TokenAuthenticationUtils tokenAuthenticationUtils;

    public TokenAuthentication(Environment environment,
                               UserRepository userRepository) {
        secret = environment.getProperty("config.security.jwt.secret");
        expirationTime = Integer.parseInt(Objects.requireNonNull(environment
                .getProperty("config.security.jwt.expiration-time")));
        refreshAfter = Integer.parseInt(Objects.requireNonNull(environment
                .getProperty("config.security.jwt.refresh-after")));
        this.userRepository = userRepository;
        tokenAuthenticationUtils = new TokenAuthenticationUtils(secret);
    }

    public boolean checkUser(String username, String token) {
        if (token == null) {
            return false;
        }
        return tokenAuthenticationUtils.getUsername(token).equalsIgnoreCase(username);
    }

    public String getAuthoritiesSeparatedByComaFromRoles(List<RoleDto> roles) {
        return tokenAuthenticationUtils.getAuthoritiesSeparatedByComa(roles
                .stream()
                .map(item -> item.getName())
                .collect(Collectors.toList()));
    }

    public String getUsernameFromToken(String token) {
        if (token == null) {
            return null;
        }
        return tokenAuthenticationUtils.getUsername(token);
    }
    public boolean isAdmin(String token) {
        Claims claims = tokenAuthenticationUtils.getClaims(token);
        List<GrantedAuthority> grantedAuthorities = getAuthorities(claims);
        return grantedAuthorities.stream()
                .anyMatch(item -> item.getAuthority().equals("ROLE_ADMIN"));

    }
    private List<GrantedAuthority> getAuthorities(Claims claims) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(
                (String) claims.get("roles")
        );
    }

    public String buildToken(String name, String roles) {
        long currentTime = System.currentTimeMillis();
        return TOKEN_PREFIX + " " + Jwts.builder()
                .setSubject(name)
                .claim("roles", roles)
                .setExpiration(new Date(currentTime + expirationTime))
                .setIssuedAt(new Date(currentTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    Authentication getAuthentication(HttpServletRequest request,
                                     HttpServletResponse response) throws UserNotFoundException, IOException {
        String token = request.getHeader(HEADER_NAME);

        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String user = tokenAuthenticationUtils.getUsername(claims);
            if (user != null) {
                refreshToken(response, claims);
                List<GrantedAuthority> grantedAuthorities = getAuthorities(claims);
                return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
            } else {
                return null;
            }
        }
        return null;
    }
    void checkIfCanAddAuthentication(User user) throws UserBlockedException {
        if(user.getRoles().isEmpty()) {
            throw new UserBlockedException();
        }
    }
    void addAuthentication(HttpServletResponse res,
                           Authentication auth) throws IOException, UserNotFoundException, UserBlockedException {
        User user = userRepository.getByUsername(auth.getName())
                .orElseThrow(UserNotFoundException::new);
        checkIfCanAddAuthentication(user);
        String JWT = buildToken(user.getUsername(),
                tokenAuthenticationUtils
                        .getAuthoritiesSeparatedByComa(auth.getAuthorities()));

        buildResponse(res, user, JWT);
    }
    private void refreshToken(HttpServletResponse response, Claims claims) throws UserNotFoundException, IOException {
        Date issuedAt = claims.getIssuedAt();
        if (System.currentTimeMillis() - issuedAt.getTime() > refreshAfter) {
            addRefreshedAuthentication(response, tokenAuthenticationUtils.getUsername(claims));
        }
    }

    private void addRefreshedAuthentication(HttpServletResponse response,
                                   String username) throws UserNotFoundException, IOException {
        User user = userRepository.getByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        String JWT = buildToken(user.getUsername(),
                tokenAuthenticationUtils.getAuthoritiesSeparatedByComa(
                        user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList())
                ));
        UserDto userDetails = UserConverter.getUserDto(user);
        String accountAsString = new ObjectMapper().writeValueAsString(userDetails);

        response.addHeader("Content-type", "application/json;charset=UTF-8");
        response.addHeader(HEADER_NAME, JWT);
        response.addHeader(X_USER_HEADER_NAME, accountAsString);
    }

    private void buildResponse(HttpServletResponse response,
                                      User user,
                                      String token) throws IOException {
        UserDto userDetails = UserConverter.getUserDto(user);
        String accountAsString = new ObjectMapper().writeValueAsString(userDetails);

        response.addHeader("Content-type", "application/json;charset=UTF-8");
        response.addHeader(HEADER_NAME, token);
        response.getWriter().write(accountAsString);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
