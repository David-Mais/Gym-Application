package com.davidmaisuradze.gymapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PasswordChangeDto {
    @JsonProperty("oldPassword")
    @NotBlank(message = "Old password should not be blank")
    private String oldPassword;

    @JsonProperty("newPassword")
    @NotBlank(message = "New password should not be blank")
    private String newPassword;
}
