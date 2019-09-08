package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.controller.swagger.ProjectsPage;
import org.mjaworski.backend.dto.project.ProjectDto;
import org.mjaworski.backend.dto.project.ProjectDtoWithId;
import org.mjaworski.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{username}/projects")
@Api(value = "User projects management",
        produces = "application/json")
public class UserProjectsRestController {
    private ProjectService projectService;

    @Autowired
    public UserProjectsRestController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ApiOperation(value = "Get projects for user.",
            response = ProjectsPage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own projects."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0 .. N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
    })
    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity get(@PathVariable("username") String username,
                              @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
                              @ApiParam() @RequestParam(value = "page", required = false) int page,
                              @ApiParam() @RequestParam(value = "size", required = false) int size) throws Exception {

        return ResponseEntity.ok(projectService.getForUser(username,
                PageRequest.of(page, size),
                authorization));
    }

    @ApiOperation(value = "Create project for user with given username.",
            response = ProjectDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 201, message = "Project created."),
            @ApiResponse(code = 400, message = "Project not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own projects"),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createProject(
            @PathVariable("username") String username,
            @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody ProjectDto projectDto) throws Exception {
        return ResponseEntity.status(201).body(projectService.add(username, projectDto, authorization));
    }
}
