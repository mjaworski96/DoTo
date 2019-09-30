package org.mjaworski.backend.controller.swagger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.mjaworski.backend.dto.user.UserLoginCredentialsDto;
import org.mjaworski.backend.dto.user.UserLoginResponseDto;
import org.springframework.web.bind.annotation.*;

@Api(value = "Authentication",
        basePath = "/api/login",
        description = "Authentication process")
@RequestMapping(value = "/api/login")
@RestController
public class SwaggerLoginRestController {
    /**
     * Implemented by Spring Security
     */

    @ApiOperation(value = "Login", notes = "Login with the given credentials.",
                    response = UserLoginResponseDto.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "You are logged in."),
            @ApiResponse(code = 401, message = "Credentials invalid."),
            @ApiResponse(code = 500, message = "Unknown error.")
    })
    @RequestMapping(method = RequestMethod.POST)
    void login(@RequestBody UserLoginCredentialsDto user) {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }
}
