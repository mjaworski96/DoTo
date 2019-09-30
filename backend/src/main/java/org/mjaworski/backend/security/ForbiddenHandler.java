package org.mjaworski.backend.security;

import org.mjaworski.backend.utils.HttpUtils;
import org.mjaworski.backend.utils.localization.LocalizedMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForbiddenHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        HttpUtils.buildResponse(httpServletResponse,
                LocalizedMessage.getInstance()
                        .getLocalizedMessageAsJsonString(403, "forbidden"),
                403);
    }
}
