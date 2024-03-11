package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {

    @Override
    public Trainer create(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer findByUsername(String username) {
        return null;
    }

    @Override
    public Trainer update(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer changePassword(String username, String currentPassword, String newPassword) {
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
    public List<Trainer> getTrainersNotAssigned(String username) {
        return null;
    }

    @Override
    public List<Training> getTrainingsList(Date from, Date to, String traineeName) {
        return null;
    }
}
