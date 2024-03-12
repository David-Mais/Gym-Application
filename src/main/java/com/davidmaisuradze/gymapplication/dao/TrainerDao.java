package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;

import java.sql.Date;
import java.util.List;

public interface TrainerDao {
    Trainer create(Trainer trainer);
    Trainer findByUsername(String username);
    Trainer update(Trainer trainer);
    List<Training> getTrainingsList(Date from, Date to, String traineeName);
    List<Trainer> getTrainersNotAssigned(String username);
}
