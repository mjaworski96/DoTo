package org.mjaworski.backend.exception.bad_request.invalid.user;

public class InvalidUsernameException extends InvalidUserException {
    @Override
    protected String getMessageKey() {
        return "invalid-user-username";
    }
}
