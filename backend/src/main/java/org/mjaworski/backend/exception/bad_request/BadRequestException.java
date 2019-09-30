package org.mjaworski.backend.exception.bad_request;

import org.mjaworski.backend.exception.LocalizedException;

public abstract class BadRequestException extends LocalizedException {
    public BadRequestException(Throwable causedBy) {
        super(causedBy);
    }

    public BadRequestException() {
    }

    @Override
    public int getMessageCode() {
        return 400;
    }
}
