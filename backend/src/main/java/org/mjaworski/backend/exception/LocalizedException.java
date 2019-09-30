package org.mjaworski.backend.exception;

import org.mjaworski.backend.utils.localization.LocalizedExceptionMessages;

public abstract class LocalizedException extends Exception {

    public LocalizedException(Throwable causedBy) {
        super(causedBy);
    }
    public LocalizedException() { }

    private static LocalizedExceptionMessages messages = new LocalizedExceptionMessages();

    protected abstract String getMessageKey();
    public abstract int getMessageCode();

    public String getLocalizedMessage() {
        return messages.getLocalizedContent(getMessageKey());
    }
    public String getMessage() {
        return getLocalizedMessage();
    }
}

