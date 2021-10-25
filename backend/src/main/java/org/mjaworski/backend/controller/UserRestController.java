package org.mjaworski.backend.controller;

import io.swagger.annotations.*;
import org.mjaworski.backend.controller.swagger.UsersPage;
import org.mjaworski.backend.dto.user.UserDto;
import org.mjaworski.backend.dto.user.UserRegisterDetailsDto;
import org.mjaworski.backend.dto.user.UserUpdateDataDto;
import org.mjaworski.backend.security.TokenAuthentication;
import org.mjaworski.backend.service.RoleService;
import org.mjaworski.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
@Api(value = "User management",
        produces = "application/json")
public class UserRestController {
    private UserService userService;
    private RoleService roleService;
    private TokenAuthentication tokenAuthentication;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService, TokenAuthentication tokenAuthentication) {
        this.userService = userService;
        this.roleService = roleService;
        this.tokenAuthentication = tokenAuthentication;
    }

    @ApiOperation(value = "Register new user.",
            response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 400, message = "User data not valid."),
            @ApiResponse(code = 409, message = "User data not unique."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PostMapping()
    public ResponseEntity register(
            @RequestBody UserRegisterDetailsDto userRegisterDetails,
            HttpServletResponse response) throws Exception {
        UserDto loginDetails
                = userService.addUser(userRegisterDetails, "USER");
        response.addHeader(TokenAuthentication.HEADER_NAME,
                tokenAuthentication.buildToken(
                        loginDetails.getId(),
                        tokenAuthentication.getAuthoritiesSeparatedByComaFromRoles(
                                loginDetails.getRoles())
                ));
        return ResponseEntity.status(201)
            .body(loginDetails);
    }

    @ApiOperation(value = "Updates user", response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User data updated."),
            @ApiResponse(code = 400, message = "User data not valid."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "You haven't permissions."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 409, message = "User data not unique."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity update(
            @PathVariable("id") int userId,
            @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody UserUpdateDataDto userUpdateData,
            HttpServletResponse response) throws Exception {
        UserDto user =
                userService.updateUser(userId, userUpdateData, authorization);
        response.addHeader(TokenAuthentication.HEADER_NAME,
                tokenAuthentication.buildToken(
                        user.getId(),
                        tokenAuthentication.getAuthoritiesSeparatedByComaFromRoles(
                                roleService.getUserRoles(user.getId()).getRoles())

                ));
        return ResponseEntity.ok(user);
    }
    @ApiOperation(value = "Get users data",
            response = UsersPage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "You haven't permissions."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getUsers(@ApiParam() @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @ApiParam() @RequestParam(value = "size", required = false, defaultValue = "10") int pageSize) throws Exception {
        return ResponseEntity.ok(userService.getUsers(page, pageSize));
    }
    @ApiOperation(value = "Get user data",
            response = UserDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "You haven't permissions."),
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity getUser(
            @PathVariable("id") int userId,
            @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization) throws Exception {
        return ResponseEntity.ok(userService.getUser(userId, authorization));
    }
    @ApiOperation(value = "Deletes user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "User deleted."),
            @ApiResponse(code = 401, message = "You are not authorized."),
            @ApiResponse(code = 403, message = "You haven't permissions."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteUser(
            @PathVariable("id") int userId,
            @ApiParam(hidden = true) @RequestHeader(value = "Authorization", required = false) String authorization)
            throws Exception {
        userService.deleteUser(userId, authorization);
        return ResponseEntity.noContent().build();
    }
}
