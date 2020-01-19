package org.mjaworski.backend.controller.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.mjaworski.backend.dto.user.UserDto;
import org.mjaworski.backend.dto.user.UserLoginCredentialsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Authentication",
    produces = "application/json")
@RequestMapping(value = "/api/login")
@RestController
public class SwaggerLoginRestController {
    /**
     * Implemented by Spring Security
     */

    @ApiOperation(value = "Login endpoint",
                    response = UserDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "You are logged in."),
            @ApiResponse(code = 401, message = "Credentials invalid."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity login(@RequestBody UserLoginCredentialsDto user) {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }
}
