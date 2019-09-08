package org.mjaworski.backend.exception.bad_request.invalid.project;

public class InvalidProjectDescriptionException extends InvalidProjectException {
    @Override
    protected String getMessageKey() {
        return "invalid-project-description";
    }
}
