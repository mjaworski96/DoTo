package org.mjaworski.backend.exception.unauthorized;


import org.mjaworski.backend.exception.LocalizedException;

public abstract class UnauthorizedException extends LocalizedException {
    @Override
    public int getMessageCode() {
        return 401;
    }
}
