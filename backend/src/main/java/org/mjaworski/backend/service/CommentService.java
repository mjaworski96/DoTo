package org.mjaworski.backend.service;

import org.mjaworski.backend.dto.comment.CommentDto;
import org.mjaworski.backend.dto.comment.CommentDtoWithId;
import org.mjaworski.backend.dto.comment.CommentDtoWithIdList;
import org.mjaworski.backend.exception.bad_request.invalid.comment.InvalidCommentException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.CommentNotFoundException;
import org.mjaworski.backend.exception.not_found.TaskNotFoundException;

public interface CommentService {
    CommentDtoWithId getOne(int id, String token) throws CommentNotFoundException, ForbiddenException;
    CommentDtoWithIdList getAll(int taskId, String token) throws ForbiddenException, TaskNotFoundException;
    CommentDtoWithId add(int taskId, CommentDto commentDto, String token) throws TaskNotFoundException, ForbiddenException, InvalidCommentException;
    CommentDtoWithId modify(int commentId, CommentDto commentDto, String token) throws CommentNotFoundException, ForbiddenException, InvalidCommentException;
    void delete(int commentId, String token) throws ForbiddenException;
}
