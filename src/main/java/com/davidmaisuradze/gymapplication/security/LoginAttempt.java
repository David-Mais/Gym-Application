package com.davidmaisuradze.gymapplication.security;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginAttempt {
    private String username;
    private int failedAttempts;
    private LocalDateTime lockoutTime;
}
