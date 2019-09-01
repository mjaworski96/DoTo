package org.mjaworski.backend.exception.bad_request;

public class InvalidPasswordException extends BadRequestException {
    @Override
    protected String getMessageKey() {
        return "invalid-password";
    }
}
