package org.mjaworski.backend.exception.bad_request.invalid.task;

public class InvalidShortDescriptionException extends InvalidTaskException {
    @Override
    protected String getMessageKey() {
        return "invalid-task-short-description";
    }
}
