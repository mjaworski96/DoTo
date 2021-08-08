package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.task.ActiveTaskDtoList;
import org.mjaworski.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{id}/tasks")
@Api(value = "User tasks management",
        produces = "application/json")
public class UserTaskController {
    private TaskService taskService;

    @Autowired
    public UserTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "Get active tasks for user.",
            response = ActiveTaskDtoList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own tasks."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity get(@PathVariable("id") int userId,
                              @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization) throws Exception {
        return ResponseEntity.ok(taskService.getAllToDo(userId, authorization));
    }

}
