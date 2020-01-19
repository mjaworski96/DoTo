package org.mjaworski.backend.exception.forbidden;

public class UserBlockedException extends ForbiddenException {
    @Override
    protected String getMessageKey() {
        return "account-blocked";
    }
}
