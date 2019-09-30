package org.mjaworski.backend.controller.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity handle(Exception e) throws Exception {
        if (e instanceof AccessDeniedException) {
            throw e; // Use ForbiddenHandler
        }
        return org.mjaworski.backend.exception.ExceptionHandler.handle(e);
    }
}