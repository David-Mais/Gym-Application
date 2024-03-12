package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;

import java.sql.Date;
import java.util.List;

public interface TrainerService {
    Trainer create(Trainer trainer);
    Trainer findByUsername(String username, String password);
    Trainer update(Trainer trainer);
    Trainer changePassword(String username, String currentPassword, String newPassword);
    Boolean activate(String username, String password);
    Boolean deactivate(String username, String password);
    List<Trainer> getTrainersNotAssigned(String username);
    List<Training> getTrainingsList(Date from, Date to, String traineeName);
}
