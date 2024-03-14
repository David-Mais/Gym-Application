package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.model.TrainingSearchCriteria;

import java.util.List;

public interface TrainerDao {
    Trainer create(Trainer trainer);
    Trainer findByUsername(String username);
    Trainer update(Trainer trainer);
    List<Training> getTrainingsList(TrainingSearchCriteria criteria);
    List<Trainer> getTrainersNotAssigned(String username);
}
