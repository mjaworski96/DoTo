package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.dto.label.LabelDto;
import org.mjaworski.backend.dto.label.LabelDtoWithId;
import org.mjaworski.backend.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labels")
@Api(value = "Labels management",
    produces = "application/json")
public class LabelsRestController {
    private LabelService labelService;

    @Autowired
    public LabelsRestController(LabelService labelService) {
        this.labelService = labelService;
    }
    @ApiOperation(value = "Update label with given id.",
            response = LabelDtoWithId.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Label updated."),
            @ApiResponse(code = 400, message = "Label data not valid."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can edit only own labels."),
            @ApiResponse(code = 404, message = "Label not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity update(@PathVariable("id") int id,
                                 @ApiParam(hidden = true)  @RequestHeader(value = "Authorization", required = false) String authorization,
                                 @RequestBody LabelDto labelDto)  throws Exception {
        return ResponseEntity
                .ok(labelService.update(id,
                        labelDto,
                        authorization));
    }

    @ApiOperation(value = "Delete label with given id.",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Label deleted."),
            @ApiResponse(code = 401, message = "You are unauthorized"),
            @ApiResponse(code = 403, message = "You have no permissions to do it. You can delete only own labels."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity delete(@PathVariable("id") int id,
                                 @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization) {
        labelService.delete(id, authorization);
        return ResponseEntity.noContent().build();
    }
}
