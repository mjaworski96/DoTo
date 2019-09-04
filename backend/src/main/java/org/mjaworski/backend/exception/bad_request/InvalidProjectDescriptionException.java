package org.mjaworski.backend.exception.bad_request;

public class InvalidProjectDescriptionException extends BadRequestException {
    @Override
    protected String getMessageKey() {
        return "invalid-project-description";
    }
}
