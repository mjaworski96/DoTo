package org.mjaworski.backend.exception.not_found;

public class ProjectNotFoundException extends NotFoundException {
    @Override
    protected String getMessageKey() {
        return "project-not-found";
    }
}
