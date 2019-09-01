package org.mjaworski.backend.exception.bad_request;

public class InvalidUsernameException extends BadRequestException {
    @Override
    protected String getMessageKey() {
        return "invalid-username";
    }
}
