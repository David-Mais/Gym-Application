package com.davidmaisuradze.gymapplication.dto.training;

import jakarta.validation.constraints.NotBlank;
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
public class CreateTrainingDto {
    @NotBlank(message = "Trainee username should not be blank")
    private String traineeUsername;
    @NotBlank(message = "Trainer username should not be blank")
    private String trainerUsername;
    @NotBlank(message = "Training name should not be blank")
    private String trainingName;
    @NotBlank(message = "Training date should not be blank")
    private LocalDate trainingDate;
    @NotBlank(message = "Training duration should not be blank")
    private Integer duration;
}
