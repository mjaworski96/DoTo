package org.mjaworski.backend.exception.not_found;

public class LabelNotFoundException extends NotFoundException {
    @Override
    protected String getMessageKey() {
        return "label-not-found";
    }
}