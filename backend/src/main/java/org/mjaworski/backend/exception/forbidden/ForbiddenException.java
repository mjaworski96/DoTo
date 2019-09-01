package org.mjaworski.backend.exception.forbidden;

import org.mjaworski.backend.exception.LocalizedException;

public class ForbiddenException extends LocalizedException {
    @Override
    protected String getMessageKey() {
        return "forbidden";
    }

    @Override
    public int getMessageCode() {
        return 403;
    }
}
