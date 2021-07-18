package org.mjaworski.backend.exception.bad_request.invalid.label;

public class InvalidLabelNameException extends InvalidLabelException {
    @Override
    protected String getMessageKey() {
        return "invalid-label-name";
    }
}
