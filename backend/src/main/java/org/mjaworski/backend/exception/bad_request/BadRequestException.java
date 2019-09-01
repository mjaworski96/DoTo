package org.mjaworski.backend.exception.bad_request;

import org.mjaworski.backend.exception.LocalizedException;

public abstract class BadRequestException extends LocalizedException {
    @Override
    public int getMessageCode() {
        return 400;
    }
}
