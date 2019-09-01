package org.mjaworski.backend.exception.unauthorized;

public class UserNotFoundException extends UnauthorizedException {
    @Override
    protected String getMessageKey() {
        return  "user-not-found";
    }
}
