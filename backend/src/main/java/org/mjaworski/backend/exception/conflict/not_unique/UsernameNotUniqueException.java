package org.mjaworski.backend.exception.conflict.not_unique;

public class UsernameNotUniqueException extends DataNotUniqueException {
    @Override
    protected String getMessageKey() {
        return "username-not-unique";
    }
}
