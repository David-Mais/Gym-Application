package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.entity.Trainee;

import java.util.List;

public interface TraineeService {
    CredentialsDto create(CreateTraineeDto traineeDto);
    TraineeDto login(CredentialsDto credentialsDto);
    TraineeProfileDto getProfile(CredentialsDto credentialsDto);
    TraineeProfileUpdateResponseDto updateProfile(TraineeProfileUpdateRequestDto updateRequestDto);
    Trainee findByUsername(String username, String password);
    Trainee update(Trainee trainee);
    void deleteByUsername(CredentialsDto credentialsDto);
    boolean changePassword(PasswordChangeDto passwordChangeDto);
    Boolean activate(CredentialsDto credentialsDto);
    Boolean deactivate(CredentialsDto credentialsDto);
    List<TrainingInfoDto> getTrainingsList(CredentialsDto credentialsDto, TrainingSearchCriteria criteria);
}
