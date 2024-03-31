package com.davidmaisuradze.gymapplication.dto.trainer;

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
public class TrainerTrainingSearchDto {
    private LocalDate from;
    private LocalDate to;
    private String name;
}
