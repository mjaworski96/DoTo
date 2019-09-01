package org.mjaworski.backend.exception.conflict;

import org.mjaworski.backend.exception.bad_request.BadRequestException;

public class UsernameNotUniqueException extends BadRequestException {
    @Override
    protected String getMessageKey() {
        return "username-not-unique";
    }
}
