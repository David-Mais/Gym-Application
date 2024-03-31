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
    TraineeProfileDto getProfile(String username);
    TraineeProfileUpdateResponseDto updateProfile(String username, TraineeProfileUpdateRequestDto updateRequestDto);
    Trainee findByUsername(String username, String password);
    Trainee update(Trainee trainee);
    void deleteByUsername(String username);
    boolean changePassword(String username, PasswordChangeDto passwordChangeDto);
    void activate(String username);
    void deactivate(String username);
    List<TrainingInfoDto> getTrainingsList(String username, TrainingSearchCriteria criteria);
}
