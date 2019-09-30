package org.mjaworski.backend.exception.not_found;

public class StateNotFoundException extends NotFoundException {
    @Override
    protected String getMessageKey() {
        return "state-not-found";
    }
}
