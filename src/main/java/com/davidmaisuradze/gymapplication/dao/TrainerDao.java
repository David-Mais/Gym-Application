package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;

import java.util.List;

public interface TrainerDao {
    Trainer create(Trainer trainer);
    Trainer findByUsername(String username);
    Trainer update(Trainer trainer);
    List<Training> getTrainingsList(TrainerTrainingSearchDto criteria);
    List<Trainer> getTrainersNotAssigned(String username);
    List<Trainer> findAll();
    List<Trainee> getAllTrainees(String username);
}