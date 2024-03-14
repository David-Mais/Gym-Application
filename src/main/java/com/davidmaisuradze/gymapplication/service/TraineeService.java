package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.model.TrainingSearchCriteria;

import java.util.List;

public interface TraineeService {
    Trainee create(Trainee trainee);
    Trainee findByUsername(String username, String password);
    Trainee update(Trainee trainee);
    void deleteByUsername(String username, String password);
    Trainee changePassword(String username, String currentPassword, String newPassword);
    Boolean activate(String username, String password);
    Boolean deactivate(String username, String password);
    List<Training> getTrainingsList(TrainingSearchCriteria criteria);
}
