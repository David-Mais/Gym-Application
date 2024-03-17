package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.model.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import com.davidmaisuradze.gymapplication.service.util.DetailsGenerator;
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
    private final DetailsGenerator detailsGenerator;
    private static final String CREDENTIAL_MATCH = "Credentials checked username and password matched";
    private static final String CREDENTIAL_MISMATCH = "Username and password not matched";

    @Autowired
    public TrainerServiceImpl(
            TrainerDao trainerDao,
            UserDao userDao,
            DetailsGenerator detailsGenerator
    ) {
        this.trainerDao = trainerDao;
        this.userDao = userDao;
        this.detailsGenerator = detailsGenerator;
        log.info("All dependencies injected");
    }

    @Override
    @Transactional
    public Trainer create(Trainer trainer) {
        String password = detailsGenerator.generatePassword();
        String username = detailsGenerator.generateUsername(
                trainer.getFirstName(),
                trainer.getLastName()
        );
        log.info("Username and password generated for Trainer: {}", trainer);
        trainer.setPassword(password);
        trainer.setUsername(username);
        trainerDao.create(trainer);
        log.info("Trainer Created");
        return trainer;
    }

    @Override
    public Trainer findByUsername(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainer trainer = trainerDao.findByUsername(username);
            if (trainer == null) {
                log.warn("Trainer with username: {} not found", username);
                return null;
            }
            return trainer;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public Trainer update(Trainer trainer) {
        if (userDao.checkCredentials(trainer.getUsername(), trainer.getPassword())) {
            log.info(CREDENTIAL_MATCH);
            return trainerDao.update(trainer);
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public Trainer changePassword(String username, String currentPassword, String newPassword) {
        if (userDao.checkCredentials(username, currentPassword)) {
            log.info(CREDENTIAL_MATCH);
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setPassword(newPassword);
            log.info("Trainer password updated");
            return trainerDao.update(trainer);
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public Boolean activate(String username, String password) {
        boolean isActive = true;
        return getaBoolean(username, password, isActive);
    }

    @Override
    @Transactional
    public Boolean deactivate(String username, String password) {
        boolean isActive = false;
        return getaBoolean(username, password, isActive);
    }

    @Override
    public List<Trainer> getTrainersNotAssigned(String username) {
        log.info("Returning trainers not assigned to: {}", username);
        return trainerDao.getTrainersNotAssigned(username);
    }

    @Override
    public List<Training> getTrainingsList(TrainingSearchCriteria criteria) {
        log.info("Returning trainings filtered by criteria: {}", criteria);
        return trainerDao.getTrainingsList(criteria);
    }

    private Boolean getaBoolean(String username, String password, boolean isActive) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setIsActive(isActive);
            log.info("Trainer={} isActive={}", username, isActive);
            return true;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return false;
    }
}
