package org.mjaworski.backend.exception.not_found;

public class UserNotFoundException extends NotFoundException {
    @Override
    protected String getMessageKey() {
        return "user-not-found";
    }
}
