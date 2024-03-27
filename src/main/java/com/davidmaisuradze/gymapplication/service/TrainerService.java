package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.entity.Trainer;

import java.util.List;

public interface TrainerService {
    CredentialsDto create(CreateTrainerDto createTrainerDto);
    TrainerDto login(CredentialsDto credentialsDto);
    TrainerProfileDto getProfile(CredentialsDto credentialsDto);
    TrainerProfileUpdateResponseDto updateProfile(TrainerProfileUpdateRequestDto requestDto);
    Trainer findByUsername(String username, String password);
    Trainer update(Trainer trainer);
    boolean changePassword(PasswordChangeDto passwordChangeDto);
    Boolean activate(CredentialsDto credentialsDto);
    Boolean deactivate(CredentialsDto credentialsDto);
    List<TrainerInfoDto> getTrainersNotAssigned(CredentialsDto credentialsDto);
    List<TrainingInfoDto> getTrainingsList(CredentialsDto credentialsDto, TrainingSearchCriteria criteria);
}
