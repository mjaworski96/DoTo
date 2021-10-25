package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.label.LabelDto;
import org.mjaworski.backend.dto.label.LabelDtoWithId;
import org.mjaworski.backend.dto.label.LabelDtoWithIdList;
import org.mjaworski.backend.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{id}/labels")
@Api(value = "Labels management",
     produces = "application/json")
public class ProjectsLabelsRestController {
    private LabelService labelService;

    @Autowired
    public ProjectsLabelsRestController(LabelService labelService) {
        this.labelService = labelService;
    }

    @ApiOperation(value = "Get labels for project.",
            response = LabelDtoWithIdList.class)
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
        return ResponseEntity.ok(labelService.getAll(id, authorization));
    }

    @ApiOperation(value = "Create label for project with given id.",
            response = LabelDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 201, message = "Label created."),
            @ApiResponse(code = 400, message = "Label not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can see only own projects."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity createLabel(
            @PathVariable("id") int id,
            @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody LabelDto labelDto) throws Exception {
        return ResponseEntity.status(201).body(labelService.create(id, labelDto, authorization));
    }
}

