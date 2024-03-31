package com.davidmaisuradze.gymapplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CredentialsDto {
    @NotBlank(message = "Username should not be blank")
    private String username;
    @NotBlank(message = "Password should not be blank")
    private String password;
}