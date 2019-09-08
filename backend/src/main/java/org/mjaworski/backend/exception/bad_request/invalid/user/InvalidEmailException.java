package org.mjaworski.backend.exception.bad_request.invalid.user;


public class InvalidEmailException extends InvalidUserException {
    public InvalidEmailException() {
    }

    public InvalidEmailException(Throwable causedBy) {
        super(causedBy);
    }

    @Override
    protected String getMessageKey() {
        return "invalid-user-email";
    }
}
