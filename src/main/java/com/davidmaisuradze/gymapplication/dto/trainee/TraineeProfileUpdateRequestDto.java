package com.davidmaisuradze.gymapplication.dto.trainee;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TraineeProfileUpdateRequestDto {
    @NotBlank(message = "First name should not be blank")
    private String firstName;
    @NotBlank(message = "Last name should not be blank")
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "No future people allowed")
    private LocalDate dateOfBirth;
    private String address;
    @NotNull(message = "isActive field should not be null")
    private Boolean isActive;
}
