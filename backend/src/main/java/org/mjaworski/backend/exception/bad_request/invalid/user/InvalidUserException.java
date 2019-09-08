package org.mjaworski.backend.exception.bad_request.invalid.user;

import org.mjaworski.backend.exception.bad_request.BadRequestException;

public abstract class InvalidUserException extends BadRequestException {
    public InvalidUserException(Throwable causedBy) {
        super(causedBy);
    }

    public InvalidUserException() {
    }
}
