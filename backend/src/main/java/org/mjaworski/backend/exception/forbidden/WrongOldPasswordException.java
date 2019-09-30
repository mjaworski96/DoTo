package org.mjaworski.backend.exception.forbidden;

public class WrongOldPasswordException extends ForbiddenException {
    @Override
    protected String getMessageKey() {
        return "wrong-old-password";
    }
}
