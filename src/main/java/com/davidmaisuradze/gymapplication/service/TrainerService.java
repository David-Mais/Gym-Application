package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.entity.Trainer;

import java.util.List;

public interface TrainerService {
    CredentialsDto create(CreateTrainerDto createTrainerDto);
    TrainerDto login(CredentialsDto credentialsDto);
    TrainerProfileDto getProfile(String username);
    TrainerProfileUpdateResponseDto updateProfile(String username, TrainerProfileUpdateRequestDto requestDto);
    Trainer findByUsername(String username, String password);
    Trainer update(Trainer trainer);
    boolean changePassword(String username, PasswordChangeDto passwordChangeDto);
    void activate(String username);
    void deactivate(String username);
    List<TrainerInfoDto> getTrainersNotAssigned(String username);
    List<TrainingInfoDto> getTrainingsList(String username, TrainerTrainingSearchDto criteria);
}
