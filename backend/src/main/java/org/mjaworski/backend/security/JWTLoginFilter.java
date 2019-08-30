package org.mjaworski.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mjaworski.backend.dto.user.UserLoginCredentials;
import org.mjaworski.backend.exception.ExceptionHandler;
import org.mjaworski.backend.utils.HttpUtils;
import org.mjaworski.backend.utils.localization.LocalizedMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private TokenAuthentication tokenAuthenticationUtils;

    public JWTLoginFilter(String url, AuthenticationManager authManager,
                          ApplicationContext ctx) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        tokenAuthenticationUtils = ctx.getBean(TokenAuthentication.class);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        UserLoginCredentials user = new ObjectMapper()
                .readValue(req.getInputStream(), UserLoginCredentials.class);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        );
        try {
            return getAuthenticationManager().authenticate(token);
        } catch (AuthenticationException exc) {
            HttpUtils.buildResponse(res, LocalizedMessage.getInstance()
            .getLocalizedMessageAsJsonString(401, "invalid-credentials"),
                    HttpServletResponse.SC_UNAUTHORIZED);
            throw exc;
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException {

        try {
            tokenAuthenticationUtils
                    .addAuthentication(res, auth);
        } catch (Exception e) {
            ResponseEntity handledException = ExceptionHandler.handle(e);
            HttpUtils.buildResponse(res, new ObjectMapper()
                            .writeValueAsString(handledException.getBody()),
                    handledException.getStatusCode().value());
        }
    }
}