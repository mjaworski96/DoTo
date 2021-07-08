package org.mjaworski.backend.service.impl;

import org.mjaworski.backend.converter.CommentConverter;
import org.mjaworski.backend.dto.comment.CommentDto;
import org.mjaworski.backend.dto.comment.CommentDtoWithId;
import org.mjaworski.backend.dto.comment.CommentDtoWithIdList;
import org.mjaworski.backend.exception.bad_request.invalid.comment.InvalidCommentContentException;
import org.mjaworski.backend.exception.bad_request.invalid.comment.InvalidCommentException;
import org.mjaworski.backend.exception.forbidden.ForbiddenException;
import org.mjaworski.backend.exception.not_found.CommentNotFoundException;
import org.mjaworski.backend.exception.not_found.TaskNotFoundException;
import org.mjaworski.backend.persistance.entity.Comment;
import org.mjaworski.backend.persistance.entity.Task;
import org.mjaworski.backend.persistance.repository.CommentRepository;
import org.mjaworski.backend.persistance.repository.TaskRepository;
import org.mjaworski.backend.security.TokenAuthentication;
import org.mjaworski.backend.service.CommentService;
import org.mjaworski.backend.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    private CommentRepository commentRepository;
    private TaskRepository taskRepository;
    private TokenAuthentication tokenAuthentication;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, TaskRepository taskRepository, TokenAuthentication tokenAuthentication) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.tokenAuthentication = tokenAuthentication;
    }

    @Override
    public CommentDtoWithId getOne(int id, String token) throws CommentNotFoundException, ForbiddenException {
        Comment comment = getComment(id);
        checkOwner(comment, token);
        return CommentConverter.getCommentDtoWithId(comment);
    }

    @Override
    public CommentDtoWithIdList getAll(int taskId, String token) throws ForbiddenException, TaskNotFoundException {
        Task task = getTask(taskId);
        checkOwner(task, token);
        List<CommentDtoWithId> comments = CommentConverter.getTaskDtoWithIdList(task.getComments());
        return CommentDtoWithIdList.builder()
                .comments(comments)
                .build();
    }

    @Override
    public CommentDtoWithId add(int taskId, CommentDto commentDto, String token) throws TaskNotFoundException, ForbiddenException, InvalidCommentException {
        validate(commentDto);
        Task task = getTask(taskId);
        checkOwner(task, token);
        Comment comment = CommentConverter.getTask(commentDto);
        comment.setTask(task);
        commentRepository.save(comment);
        return CommentConverter.getCommentDtoWithId(comment);
    }

    @Override
    public CommentDtoWithId modify(int commentId, CommentDto commentDto, String token) throws CommentNotFoundException, ForbiddenException, InvalidCommentException {
        validate(commentDto);
        Comment comment = getComment(commentId);
        checkOwner(comment, token);
        CommentConverter.rewrite(comment, commentDto);
        commentRepository.save(comment);
        return CommentConverter.getCommentDtoWithId(comment);
    }

    @Override
    public void delete(int commentId, String token) throws ForbiddenException {
        try {
            Optional<Comment> comment = commentRepository.get(commentId);
            if (comment.isPresent()) {
                checkOwner(comment.get(), token);
                commentRepository.delete(comment.get());
            }
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Comment ({}) already deleted", commentId);
        }
    }
    private Task getTask(int id) throws TaskNotFoundException {
        return taskRepository.get(id)
                .orElseThrow(TaskNotFoundException::new);
    }

    private Comment getComment(int id) throws CommentNotFoundException {
        return commentRepository.get(id)
                .orElseThrow(CommentNotFoundException::new);
    }
    private void checkOwner(Task task, String token) throws ForbiddenException {
        if(!tokenAuthentication.checkUser(task.getProject().getOwner().getId(), token))
            throw new ForbiddenException();
    }

    private void checkOwner(Comment comment, String token) throws ForbiddenException {
        checkOwner(comment.getTask(), token);
    }

    private void validate(CommentDto commentDto) throws InvalidCommentContentException {
        if(!ValidationUtils.isValid(commentDto.getContent(),
                Comment.MIN_CONTENT_LENGTH,
                Comment.MAX_CONTENT_LENGTH))
            throw new InvalidCommentContentException();
    }

}
