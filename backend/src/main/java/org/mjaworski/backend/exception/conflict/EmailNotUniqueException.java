package org.mjaworski.backend.exception.conflict;

public class EmailNotUniqueException extends ConflictException {
    @Override
    protected String getMessageKey() {
        return "email-not-unique";
    }
}
