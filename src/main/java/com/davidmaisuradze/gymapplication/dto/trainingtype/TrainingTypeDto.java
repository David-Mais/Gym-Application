package com.davidmaisuradze.gymapplication.dto.trainingtype;

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
public class TrainingTypeDto {
    private Long id;
    private String trainingTypeName;
}
