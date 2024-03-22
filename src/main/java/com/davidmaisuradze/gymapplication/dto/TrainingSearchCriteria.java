package com.davidmaisuradze.gymapplication.dto;

import com.davidmaisuradze.gymapplication.entity.TrainingType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class TrainingSearchCriteria {
    private LocalDate from;
    private LocalDate to;
    private String name;
    private TrainingType trainingType;
}
