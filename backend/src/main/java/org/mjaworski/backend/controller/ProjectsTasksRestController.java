package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.task.TaskDto;
import org.mjaworski.backend.dto.task.TaskDtoWithId;
import org.mjaworski.backend.dto.task.TaskDtoWithIdList;
import org.mjaworski.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{id}/tasks")
@Api(value = "Projects tasks management",
        produces = "application/json")
public class ProjectsTasksRestController {
    private TaskService taskService;

    @Autowired
    public ProjectsTasksRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "Get tasks for project.",
            response = TaskDtoWithIdList.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own projects."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity get(@PathVariable("id") int id,
                              @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization) throws Exception {
        return ResponseEntity.ok(taskService.getAll(id,
                authorization));
    }

    @ApiOperation(value = "Create task for project with given id.",
            response = TaskDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 201, message = "Task created."),
            @ApiResponse(code = 400, message = "Task not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own projects."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createTask(
            @PathVariable("id") int id,
            @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody TaskDto taskDto) throws Exception {
        return ResponseEntity.status(201).body(taskService.add(id, taskDto, authorization));
    }
}
