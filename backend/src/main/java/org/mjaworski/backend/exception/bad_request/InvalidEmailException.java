package org.mjaworski.backend.exception.bad_request;

public class InvalidEmailException extends BadRequestException {
    public InvalidEmailException() {
    }

    public InvalidEmailException(Throwable causedBy) {
        super(causedBy);
    }

    @Override
    protected String getMessageKey() {
        return "invalid-email";
    }
}
