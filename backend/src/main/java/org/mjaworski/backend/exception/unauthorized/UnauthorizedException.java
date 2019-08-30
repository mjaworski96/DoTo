package org.mjaworski.backend.exception.unauthorized;


import org.mjaworski.backend.exception.LocalizedException;

public abstract class UnauthorizedException extends LocalizedException {
    public UnauthorizedException(Throwable causedBy) {
        super(causedBy);
    }

    public UnauthorizedException() {
    }

    @Override
    public int getMessageCode() {
        return 401;
    }
}
