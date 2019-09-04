package org.mjaworski.backend.exception.bad_request;

public class InvalidProjectNameException extends BadRequestException {
    @Override
    protected String getMessageKey() {
        return "invalid-project-name";
    }
}
