package com.davidmaisuradze.gymapplication.dto.training;

import com.davidmaisuradze.gymapplication.entity.TrainingType;
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
public class TrainingInfoDto {
    private String trainingName;
    private LocalDate trainingDate;
    private TrainingType trainingType;
    private Integer duration;
    private String username;
}
