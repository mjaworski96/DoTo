package org.mjaworski.backend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.mjaworski.backend.dto.user.ResetPasswordDto;
import org.mjaworski.backend.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/password")
@Api(value = "Password management",
        produces = "application/json")
public class PasswordRestController {

    private PasswordService passwordService;

    @Autowired
    public PasswordRestController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @ApiOperation(value = "Reset password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Not used."),
            @ApiResponse(code = 204, message = "Ok."),
            @ApiResponse(code = 401, message = "Not used."),
            @ApiResponse(code = 403, message = "Not used."),
            @ApiResponse(code = 404, message = "Not used."),
            @ApiResponse(code = 500, message = "Unknown error"),
    })
    @PostMapping("/reset")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDto passwordResetRequest) throws Exception {
        passwordService.resetPassword(passwordResetRequest);
        return ResponseEntity.noContent().build();
    }
}
