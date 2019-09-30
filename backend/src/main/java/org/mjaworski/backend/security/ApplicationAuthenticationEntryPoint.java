package org.mjaworski.backend.security;

import org.mjaworski.backend.utils.HttpUtils;
import org.mjaworski.backend.utils.localization.LocalizedMessage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpUtils.buildResponse(response,
                LocalizedMessage.getInstance()
                        .getLocalizedMessageAsJsonString(401, "unauthorized"),
                401);
    }
}