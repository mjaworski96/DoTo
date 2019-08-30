package org.mjaworski.backend.exception.internal_server_error;

import org.mjaworski.backend.exception.LocalizedException;

public class InternalServerErrorException extends LocalizedException {
    public InternalServerErrorException(Throwable causedBy) {
        super(causedBy);
    }

    public InternalServerErrorException() {
    }

    @Override
    protected String getMessageKey() {
        return "unknown";
    }

    @Override
    public int getMessageCode() {
        return 500;
    }
}
