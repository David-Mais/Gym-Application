package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;

import java.sql.Date;
import java.util.List;

public interface TraineeService {
    Trainee create(Trainee trainee);
    Trainee findByUsername(String username);
    Trainee update(Trainee trainee);
    Trainee deleteByUsername(String username);
    Trainee changePassword(String username, String currentPassword, String newPassword);
    Boolean activate();
    Boolean deactivate();
    Trainee updateTrainersList(String username, List<Trainer> trainers);
    List<Training> getTrainingsList(Date from, Date to, String trainerName, TrainingType trainingType);
}
