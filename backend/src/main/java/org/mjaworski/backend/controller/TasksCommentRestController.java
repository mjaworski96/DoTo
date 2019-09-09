package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.comment.CommentDto;
import org.mjaworski.backend.dto.task.TaskDtoWithId;
import org.mjaworski.backend.dto.task.TaskDtoWithIdList;
import org.mjaworski.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks/{id}/comments")
@Api(value = "Tasks comments management",
        produces = "application/json")
public class TasksCommentRestController {
    private CommentService commentService;

    @Autowired
    public TasksCommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }
    @ApiOperation(value = "Get comments for task.",
            response = TaskDtoWithIdList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own tasks."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity get(@PathVariable("id") int id,
                              @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization) throws Exception {
        return ResponseEntity.ok(commentService.getAll(id,
                authorization));
    }

    @ApiOperation(value = "Create comment for task with given id.",
            response = TaskDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 201, message = "Comment created."),
            @ApiResponse(code = 400, message = "Comment not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own tasks."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createComment(
            @PathVariable("id") int id,
            @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody CommentDto commentDto) throws Exception {
        return ResponseEntity.status(201).body(commentService.add(id, commentDto, authorization));
    }
}
