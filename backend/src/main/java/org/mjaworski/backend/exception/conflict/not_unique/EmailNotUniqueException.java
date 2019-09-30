package org.mjaworski.backend.exception.conflict.not_unique;

public class EmailNotUniqueException extends DataNotUniqueException {
    @Override
    protected String getMessageKey() {
        return "email-not-unique";
    }
}
