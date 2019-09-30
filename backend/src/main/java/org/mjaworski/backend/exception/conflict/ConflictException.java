package org.mjaworski.backend.exception.conflict;

import org.mjaworski.backend.exception.LocalizedException;

public abstract class ConflictException extends LocalizedException {
    @Override
    public int getMessageCode() {
        return 409;
    }
}
