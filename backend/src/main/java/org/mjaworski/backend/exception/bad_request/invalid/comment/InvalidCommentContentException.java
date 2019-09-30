package org.mjaworski.backend.exception.bad_request.invalid.comment;

public class InvalidCommentContentException extends InvalidCommentException {
    @Override
    protected String getMessageKey() {
        return "invalid-comment-content";
    }
}
