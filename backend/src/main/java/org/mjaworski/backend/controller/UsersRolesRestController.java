package org.mjaworski.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.mjaworski.backend.dto.role.RolesChangesDto;
import org.mjaworski.backend.dto.role.RolesDto;
import org.mjaworski.backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{username}/roles")
@Api(value = "User roles management",
        basePath = "/api/users/{username}/roles",
        produces = "application/json",
        description = "Change user roles")
public class UsersRolesRestController {
    private RoleService roleService;

    @Autowired
    public UsersRolesRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "Add or remove role for user with given id.",
            response = RolesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "You haven't permissions."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PatchMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity updateRoles(
            @PathVariable("username") String username,
            @RequestBody RolesChangesDto roles) throws Exception {
        return ResponseEntity.ok(roleService.updateRoles(username, roles));
    }
}
