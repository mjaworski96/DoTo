package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.project.ProjectDto;
import org.mjaworski.backend.dto.project.ProjectDtoWithId;
import org.mjaworski.backend.dto.project.ProjectStateChange;
import org.mjaworski.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@Api(value = "Projects management",
        produces = "application/json")
public class ProjectsRestController {
    private ProjectService projectService;

    @Autowired
    public ProjectsRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation(value = "Get project with given id.",
            response = ProjectDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own projects"),
            @ApiResponse(code = 404, message = "Project not found."),
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") int id,
                              @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization)
            throws Exception {
        return ResponseEntity.ok(projectService.getOne(id, authorization));
    }

    @ApiOperation(value = "Update project with given id.",
            response = ProjectDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Project updated."),
            @ApiResponse(code = 400, message = "Project data not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can edit only own projects"),
            @ApiResponse(code = 404, message = "Project not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity update(@PathVariable("id") int id,
                                 @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization,
                                 @RequestBody ProjectDto projectDto)  throws Exception {
        return ResponseEntity
                .ok(projectService.modify(id,
                        projectDto,
                        authorization));
    }
    @ApiOperation(value = "Delete project with given id.",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Project deleted."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can delete only own projects"),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization)
            throws Exception {
        projectService.delete(id, authorization);
        return ResponseEntity.noContent().build();
    }
    @ApiOperation(value = "Update project with given id.",
            response = ProjectDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Project updated."),
            @ApiResponse(code = 400, message = "Project data not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can edit only own projects"),
            @ApiResponse(code = 404, message = "Project not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PutMapping("/{id}/state")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity updateState(@PathVariable("id") int id,
                                 @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization,
                                 @RequestBody ProjectStateChange stateChange)  throws Exception {
        return ResponseEntity
                .ok(projectService.modifyState(id,
                        stateChange,
                        authorization));
    }
}
