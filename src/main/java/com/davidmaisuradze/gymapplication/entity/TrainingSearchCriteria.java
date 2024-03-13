package com.davidmaisuradze.gymapplication.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
public class TrainingSearchCriteria {
    private LocalDate from;
    private LocalDate to;
    private String name;
    private TrainingType trainingType;
}
