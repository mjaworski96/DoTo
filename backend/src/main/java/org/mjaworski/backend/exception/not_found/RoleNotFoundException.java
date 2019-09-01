package org.mjaworski.backend.exception.not_found;

public class RoleNotFoundException extends NotFoundException {
    @Override
    protected String getMessageKey() {
        return "role-not-found";
    }
}
