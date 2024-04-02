package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody CredentialsDto credentialsDto) {
        return ResponseEntity.ok().body(userService.login(credentialsDto));
    }

    @PutMapping("/password/{username}")
    public ResponseEntity<Boolean> changePassword(
            @PathVariable String username,
            @Valid @RequestBody PasswordChangeDto passwordChangeDto
    ) {
        return ResponseEntity
                .ok()
                .body(userService.changePassword(username, passwordChangeDto));
    }
}
