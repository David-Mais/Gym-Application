package com.davidmaisuradze.gymapplication.dto.trainer;

import com.davidmaisuradze.gymapplication.dto.trainee.TraineeInfoDto;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrainerProfileUpdateResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private TrainingType specialization;
    private Boolean isActive;
    private List<TraineeInfoDto> traineesList;
}
