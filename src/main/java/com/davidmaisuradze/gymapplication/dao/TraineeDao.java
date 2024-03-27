package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;

import java.util.List;

public interface TraineeDao {
    Trainee create(Trainee trainee);
    Trainee findByUsername(String username);
    Trainee update(Trainee trainee);
    void delete(Trainee trainee);
    List<Training> getTrainingsList(TrainingSearchCriteria criteria);
    List<Trainer> getAllTrainers(String username);
    List<Trainee> findAll();
}
