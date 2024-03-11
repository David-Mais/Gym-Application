package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {

    @Override
    public Trainee create(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee findByUsername(String username) {
        return null;
    }

    @Override
    public Trainee update(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee deleteByUsername(String username) {
        return null;
    }

    @Override
    public Trainee changePassword(String username, String currentPassword, String newPassword) {
        return null;
    }

    @Override
    public Boolean activate() {
        return null;
    }

    @Override
    public Boolean deactivate() {
        return null;
    }

    @Override
    public Trainee updateTrainersList(String username, List<Trainer> trainers) {
        return null;
    }

    @Override
    public List<Training> getTrainingsList(Date from, Date to, String trainerName, TrainingType trainingType) {
        return null;
    }
}
