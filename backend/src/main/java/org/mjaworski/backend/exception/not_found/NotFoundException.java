package org.mjaworski.backend.exception.not_found;

import org.mjaworski.backend.exception.LocalizedException;

public abstract class NotFoundException extends LocalizedException {
    @Override
    public int getMessageCode() {
        return 404;
    }
}
