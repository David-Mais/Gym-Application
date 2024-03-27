package com.davidmaisuradze.gymapplication.dto.trainee;

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
public class CreateTraineeDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
}
