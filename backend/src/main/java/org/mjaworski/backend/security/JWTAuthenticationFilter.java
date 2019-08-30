package org.mjaworski.backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.mjaworski.backend.exception.unauthorized.UserNotFoundException;
import org.mjaworski.backend.utils.HttpUtils;
import org.mjaworski.backend.utils.localization.LocalizedMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private TokenAuthentication tokenAuthenticationUtils;

    public JWTAuthenticationFilter(ApplicationContext ctx) {
        tokenAuthenticationUtils = ctx.getBean(TokenAuthentication.class);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        try {
            Authentication authentication = tokenAuthenticationUtils
                    .getAuthentication((HttpServletRequest) request,
                            (HttpServletResponse) response);
            
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            build401Response(response, "token-expired");
        } catch (SignatureException e) {
            build401Response(response, "unauthorized");
        } catch (UserNotFoundException e) {
            build401Response(response, "user-not-found");
        }
    }
    private void build401Response(ServletResponse response, String messageKey) throws IOException {
        HttpServletResponse httpResponse =(HttpServletResponse) response;

        HttpUtils.buildResponse(httpResponse, LocalizedMessage.getInstance()
                        .getLocalizedMessageAsJsonString(HttpServletResponse.SC_UNAUTHORIZED, messageKey),
                HttpServletResponse.SC_UNAUTHORIZED);
    }
}