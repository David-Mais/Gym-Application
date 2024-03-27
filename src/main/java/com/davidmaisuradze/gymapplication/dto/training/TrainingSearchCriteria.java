package com.davidmaisuradze.gymapplication.dto.training;

import com.davidmaisuradze.gymapplication.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSearchCriteria {
    private LocalDate from;
    private LocalDate to;
    private String name;
    private TrainingType trainingType;
}
