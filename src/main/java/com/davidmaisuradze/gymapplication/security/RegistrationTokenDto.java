package com.davidmaisuradze.gymapplication.security;

import com.davidmaisuradze.gymapplication.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationTokenDto {
    private String username;
    private String password;
    private Token token;
}
