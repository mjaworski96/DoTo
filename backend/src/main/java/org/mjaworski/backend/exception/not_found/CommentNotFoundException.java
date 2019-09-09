package org.mjaworski.backend.exception.not_found;

public class CommentNotFoundException extends NotFoundException {
    @Override
    protected String getMessageKey() {
        return "comment-not-found";
    }
}
