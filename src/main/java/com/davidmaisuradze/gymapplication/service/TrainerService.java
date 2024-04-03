package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;

import java.util.List;

public interface TrainerService {
    CredentialsDto create(CreateTrainerDto createTrainerDto);
    TrainerProfileDto getProfile(String username);
    TrainerProfileUpdateResponseDto updateProfile(String username, TrainerProfileUpdateRequestDto requestDto);
    void updateActiveStatus(String username, ActiveStatusDto activeStatusDto);
    List<TrainerInfoDto> getTrainersNotAssigned(String username);
    List<TrainingInfoDto> getTrainingsList(String username, TrainerTrainingSearchDto criteria);
}
