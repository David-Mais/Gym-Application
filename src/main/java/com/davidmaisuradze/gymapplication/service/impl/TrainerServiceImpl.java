package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private final UserDao userDao;

    @Autowired
    public TrainerServiceImpl(
            TrainerDao trainerDao,
            UserDao userDao
    ) {
        this.trainerDao = trainerDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Trainer create(Trainer trainer) {
        String password = userDao.generatePassword();
        String username = userDao.generateUsername(
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
        if (userDao.checkCredentials(username, password)) {
            Trainer trainer = trainerDao.findByUsername(username);
            if (trainer == null) {
                log.warn("Trainer with username: {} not found", username);
                return null;
            }
            return trainer;
        }
        log.warn("Username: {} and Password: {} does not match", username, password);
        return null;
    }

    @Override
    @Transactional
    public Trainer update(Trainer trainer) {
        if (userDao.checkCredentials(trainer.getUsername(), trainer.getPassword())) {
            return trainerDao.update(trainer);
        }
        return null;
    }

    @Override
    @Transactional
    public Trainer changePassword(String username, String currentPassword, String newPassword) {
        if (userDao.checkCredentials(username, currentPassword)) {
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setPassword(newPassword);
            return trainerDao.update(trainer);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean activate(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setIsActive(true);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deactivate(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
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
    public List<Training> getTrainingsList(TrainingSearchCriteria criteria) {
        return trainerDao.getTrainingsList(criteria);
    }
}
