package org.mjaworski.backend.exception.bad_request.invalid.task;

public class InvalidFullDescriptionException extends InvalidTaskException {
    @Override
    protected String getMessageKey() {
        return "invalid-task-full-description";
    }
}
