package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;

import java.sql.Date;
import java.util.List;

public interface TrainerService {
    Trainer create(Trainer trainer);
    Trainer findByUsername(String username);
    Trainer update(Trainer trainer);
    Trainer changePassword(String username, String currentPassword, String newPassword);
    Boolean activate();
    Boolean deactivate();
    List<Trainer> getTrainersNotAssigned(String username);
    List<Training> getTrainingsList(Date from, Date to, String traineeName);
}
