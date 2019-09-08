package org.mjaworski.backend.exception.not_found;

public class TaskNotFoundException extends NotFoundException {
    @Override
    protected String getMessageKey() {
        return "task-not-found";
    }
}
