package org.mjaworski.backend.exception.bad_request.invalid.user;

public class InvalidPasswordException extends InvalidUserException {
    @Override
    protected String getMessageKey() {
        return "invalid-user-password";
    }
}
