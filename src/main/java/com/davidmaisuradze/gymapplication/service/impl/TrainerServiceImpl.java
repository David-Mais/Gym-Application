package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import com.davidmaisuradze.gymapplication.utils.Authenticator;
import com.davidmaisuradze.gymapplication.utils.Generator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private final Generator generator;
    private final Authenticator authenticator;

    @Autowired
    public TrainerServiceImpl(
            TrainerDao trainerDao,
            Generator generator,
            Authenticator authenticator
    ) {
        this.trainerDao = trainerDao;
        this.generator = generator;
        this.authenticator = authenticator;
    }

    @Override
    @Transactional
    public Trainer create(Trainer trainer) {
        String password = generator.generatePassword();
        String username = generator.generateUsername(
                trainer.getFirstName(),
                trainer.getLastName()
        );
        trainer.setPassword(password);
        trainer.setUsername(username);
        trainerDao.create(trainer);
        return trainer;
    }

    @Override
    @Transactional
    public Trainer findByUsername(String username, String password) {
        if (authenticator.checkCredentials(username, password)) {
            return trainerDao.findByUsername(username);
        }
        return null;
    }

    @Override
    @Transactional
    public Trainer update(Trainer trainer) {
        if (authenticator.checkCredentials(trainer.getUsername(), trainer.getPassword())) {
            return trainerDao.update(trainer);
        }
        return null;
    }

    @Override
    @Transactional
    public Trainer changePassword(String username, String currentPassword, String newPassword) {
        if (authenticator.checkCredentials(username, currentPassword)) {
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setPassword(newPassword);
            return trainerDao.update(trainer);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean activate(String username, String password) {
        if (authenticator.checkCredentials(username, password)) {
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setIsActive(true);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deactivate(String username, String password) {
        if (authenticator.checkCredentials(username, password)) {
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setIsActive(false);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Trainer> getTrainersNotAssigned(String username) {
        return trainerDao.getTrainersNotAssigned(username);
    }

    @Override
    @Transactional
    public List<Training> getTrainingsList(Date from, Date to, String traineeName) {
        return trainerDao.getTrainingsList(from, to, traineeName);
    }
}
