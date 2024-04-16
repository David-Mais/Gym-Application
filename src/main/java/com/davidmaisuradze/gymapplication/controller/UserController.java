package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.ErrorDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoint for managing users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    @Operation(
            description = "Simulate login to see if the credentials that are provided actually match or not.",
            summary = "Login",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "json",
                            schema = @Schema(implementation = CredentialsDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid credentials",
                                            description = "Scenario where credentials are correctly provided.",
                                            value = """
                                                    {
                                                      "username": "Some.user",
                                                      "password": "correctPass"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Invalid credentials",
                                            description = "Scenario where credentials are not correctly provided.",
                                            value = """
                                                    {
                                                      "username": "Some.user"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Credentials matched",
                            content = @Content(
                                    mediaType = "json",
                                    schema = @Schema(implementation = Boolean.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Successful login",
                                                    description = "Credentials matched so the login is considered successful.",
                                                    value = "true"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "When username and/or password is not provided it causes validation error " +
                                    "and returns status code 400 with appropriate error dto.",
                            content = @Content(
                                    mediaType = "json",
                                    schema = @Schema(implementation = ErrorDto.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "No username",
                                                    description = "No username provided in CredentialsDto.",
                                                    value = """
                                                            {
                                                              "errorMessage": "Validation error",
                                                              "details": {
                                                                "username": "username should not be blank"
                                                              },
                                                              "errorCode": "400"
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "No password",
                                                    description = "No password provided in CredentialsDto.",
                                                    value = """
                                                            {
                                                              "errorMessage": "Validation error",
                                                              "details": {
                                                                "password": "Password should not be blank"
                                                              },
                                                              "errorCode": "400"
                                                            }
                                                            """
                                            ),
                                            @ExampleObject(
                                                    name = "No username no password",
                                                    description = "No username and password provided in CredentialsDto.",
                                                    value = """
                                                            {
                                                              "errorMessage": "Validation error",
                                                              "details": {
                                                                "username": "username should not be blank",
                                                                "password": "Password should not be blank"
                                                              },
                                                              "errorCode": "400"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "When username and password doesnt match API responds with 401 error code " +
                                    "unauthorized and error DTO with appropriate message.",
                            content = @Content(
                                    mediaType = "json",
                                    schema = @Schema(implementation = ErrorDto.class),
                                    examples = @ExampleObject(
                                            name = "Credentials not matched",
                                            description = "Provided username and password doesn't match in the application.",
                                            value = """
                                                    {
                                                      "errorMessage": "Invalid Credentials",
                                                      "errorCode": "401"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Boolean> login(@Valid @RequestBody CredentialsDto credentialsDto) {
        return ResponseEntity.ok().body(userService.login(credentialsDto));
    }

    @PutMapping("/password")
    @Operation(
            description = "Endpoint used for changing password of users",
            summary = "Change password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "json",
                            schema = @Schema(implementation = PasswordChangeDto.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Valid password change",
                                            description = "Valid password change dto consists of username of a user " +
                                                    "client wants to change password of, oldPassword which at the time " +
                                                    "is current password and the newPassword which in case of success would " +
                                                    "be new password.",
                                            value = """
                                                    {
                                                      "username": "Davit.Maisuradze",
                                                      "oldPassword": "newPass",
                                                      "newPassword": "actuallyNewPass"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Invalid password change",
                                            description = "Valid password change dto consists of username of a user " +
                                                    "client wants to change password of, oldPassword which at the time " +
                                                    "is current password and the newPassword which in case of success would " +
                                                    "be new password.",
                                            value = """
                                                    {
                                                      "username": "Davit.Maisuradze",
                                                      "newPassword": "actuallyNewPass"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password changed",
                            content = @Content(
                                    mediaType = "json",
                                    schema = @Schema(implementation = Boolean.class),
                                    examples = @ExampleObject(
                                            name = "Successful password change",
                                            description = "Credentials matched so the password change " +
                                                    "is successful.",
                                            value = "true"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "When username, oldPassword and/or newPassword is not provided it causes validation error " +
                                    "and returns status code 400 with appropriate error dto.",
                            content = @Content(
                                    mediaType = "json",
                                    schema = @Schema(implementation = ErrorDto.class),
                                    examples = @ExampleObject(
                                            name = "Validation error",
                                            description = "When one of the fields or multiple fields are not provided " +
                                                    "that causes validation error.",
                                            value = """
                                                    {
                                                      "errorMessage": "Validation error",
                                                      "details": {
                                                        "oldPassword": "Old password should not be blank",
                                                        "newPassword": "New password should not be blank",
                                                        "username": "Username should not be blank"
                                                      },
                                                      "errorCode": "400"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "When username and password doesnt match API responds with 401 error code " +
                                    "unauthorized and error DTO with appropriate message.",
                            content = @Content(
                                    mediaType = "json",
                                    schema = @Schema(implementation = ErrorDto.class),
                                    examples = @ExampleObject(
                                            name = "Credentials not matched",
                                            description = "Provided username and password doesn't match in the application.",
                                            value = """
                                                    {
                                                      "errorMessage": "Invalid Credentials",
                                                      "errorCode": "401"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    public ResponseEntity<Boolean> changePassword(
            @Valid @RequestBody PasswordChangeDto passwordChangeDto
    ) {
        return ResponseEntity
                .ok()
                .body(userService.changePassword(passwordChangeDto));
    }
}
