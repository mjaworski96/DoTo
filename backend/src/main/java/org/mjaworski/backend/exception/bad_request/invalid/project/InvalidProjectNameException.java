package org.mjaworski.backend.exception.bad_request.invalid.project;

public class InvalidProjectNameException extends InvalidProjectException {
    @Override
    protected String getMessageKey() {
        return "invalid-project-name";
    }
}
