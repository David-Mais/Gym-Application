package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.dto.TrainingSearchCriteria;

import java.util.List;

public interface TraineeDao {
    Trainee create(Trainee trainee);
    Trainee findByUsername(String username);
    Trainee update(Trainee trainee);
    void delete(Trainee trainee);
    List<Training> getTrainingsList(TrainingSearchCriteria criteria);
    List<Trainee> findAll();
}
