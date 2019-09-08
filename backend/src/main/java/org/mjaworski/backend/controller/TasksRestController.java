package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.task.StateDto;
import org.mjaworski.backend.dto.task.TaskDto;
import org.mjaworski.backend.dto.task.TaskDtoWithId;
import org.mjaworski.backend.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Api(value = "Tasks management",
        produces = "application/json")
public class TasksRestController {
    private TaskService taskService;

    @Autowired
    public TasksRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "Get task with given id.",
            response = TaskDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own tasks."),
            @ApiResponse(code = 404, message = "Task not found."),
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") int id,
                              @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization)
            throws Exception {
        return ResponseEntity.ok(taskService.getOne(id, authorization));
    }

    @ApiOperation(value = "Update task with given id.",
            response = TaskDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Task updated."),
            @ApiResponse(code = 400, message = "Task data not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can edit only own tasks"),
            @ApiResponse(code = 404, message = "Task not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity update(@PathVariable("id") int id,
                                 @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization,
                                 @RequestBody TaskDto taskDto)  throws Exception {
        return ResponseEntity
                .ok(taskService.modify(id,
                        taskDto,
                        authorization));
    }

    @ApiOperation(value = "Update task state with given id.",
            response = StateDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Task state updated."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can edit only own tasks state"),
            @ApiResponse(code = 404, message = "Task not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PutMapping("/{id}/state")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateState(@PathVariable("id") int id,
                                 @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization,
                                 @RequestBody StateDto stateDto)  throws Exception {
        return ResponseEntity
                .ok(taskService.updateState(id,
                        stateDto,
                        authorization));
    }

    @ApiOperation(value = "Delete task with given id.",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Task deleted."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can delete only own tasks."),
            @ApiResponse(code = 404, message = "Task not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization)
            throws Exception {
        taskService.delete(id, authorization);
        return ResponseEntity.noContent().build();
    }
}
