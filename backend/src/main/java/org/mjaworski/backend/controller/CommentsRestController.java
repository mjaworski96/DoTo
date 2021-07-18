package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.comment.CommentDto;
import org.mjaworski.backend.dto.comment.CommentDtoWithId;
import org.mjaworski.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@Api(value = "Comments management",
        produces = "application/json")
public class CommentsRestController {
    private CommentService commentService;

    @Autowired
    public CommentsRestController(CommentService commentService) {
        this.commentService = commentService;
    }
    @ApiOperation(value = "Get comment with given id.",
            response = CommentDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own comments."),
            @ApiResponse(code = 404, message = "Comment not found."),
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") int id,
                              @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization)
            throws Exception {
        return ResponseEntity.ok(commentService.getOne(id, authorization));
    }

    @ApiOperation(value = "Update comment with given id.",
            response = CommentDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Comment updated."),
            @ApiResponse(code = 400, message = "Comment data not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can edit only own comments."),
            @ApiResponse(code = 404, message = "Comment not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity update(@PathVariable("id") int id,
                                 @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization,
                                 @RequestBody CommentDto commentDto)  throws Exception {
        return ResponseEntity
                .ok(commentService.modify(id,
                        commentDto,
                        authorization));
    }

    @ApiOperation(value = "Delete comment with given id.",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Comment deleted."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can delete only own comments."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization)
            throws Exception {
        commentService.delete(id, authorization);
        return ResponseEntity.noContent().build();
    }
}
