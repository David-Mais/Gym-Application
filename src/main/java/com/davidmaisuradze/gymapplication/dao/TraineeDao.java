package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;

import java.sql.Date;
import java.util.List;

public interface TraineeDao {
    Trainee create(Trainee trainee);
    Trainee findByUsername(String username);
    Trainee update(Trainee trainee);
    Trainee deleteByUsername(String username);
    List<Training> getTrainingsList(Date from, Date to, String trainerName, TrainingType trainingType);
}
