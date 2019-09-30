package org.mjaworski.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

class TokenAuthenticationUtils {
    private String secret;

    TokenAuthenticationUtils(String secret) {
        this.secret = secret;
    }

    Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token.replace(TokenAuthentication.TOKEN_PREFIX, ""))
                .getBody();
    }
    String getUsername(Claims claims) {
        return claims.getSubject();
    }
    String getUsername(String token) {
        return getUsername(getClaims(token));
    }
    String getAuthoritiesSeparatedByComa(Collection<? extends GrantedAuthority> grantedAuthorities) {
        String notSeparated = grantedAuthorities.toString();
        return notSeparated.substring(1, notSeparated.length() - 1);
    }
    String getAuthoritiesSeparatedByComa(List<String> grantedAuthorities) {
        StringBuilder stringBuilder = new StringBuilder();
        grantedAuthorities.forEach(item -> {
            stringBuilder.append("ROLE_");
            stringBuilder.append(item);
            stringBuilder.append(",");
        });
        String authorized = stringBuilder.toString();
        return authorized.substring(0, authorized.length() - 1);
    }
}
