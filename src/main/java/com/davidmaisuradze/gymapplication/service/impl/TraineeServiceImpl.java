package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.model.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import com.davidmaisuradze.gymapplication.service.util.DetailsGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDao traineeDao;
    private final UserDao userDao;
    private final DetailsGenerator detailsGenerator;
    private static final String CREDENTIAL_MATCH = "Credentials checked username and password matched";
    private static final String CREDENTIAL_MISMATCH = "Username and password not matched";

    @Autowired
    public TraineeServiceImpl(
            TraineeDao traineeDao,
            UserDao userDao,
            DetailsGenerator detailsGenerator) {
        this.traineeDao = traineeDao;
        this.userDao = userDao;
        this.detailsGenerator = detailsGenerator;
        log.info("All dependencies injected");
    }

    @Override
    @Transactional
    public Trainee create(Trainee trainee) {
        String password = detailsGenerator.generatePassword();
        String username = detailsGenerator.generateUsername(
                trainee.getFirstName(),
                trainee.getLastName()
        );
        log.info("Username and password generated for Trainee: {}", trainee);
        trainee.setPassword(password);
        trainee.setUsername(username);
        traineeDao.create(trainee);
        log.info("Trainee Created");
        return trainee;
    }

    @Override
    public Trainee findByUsername(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainee trainee = traineeDao.findByUsername(username);
            if (trainee == null) {
                log.warn("Trainee with username: {} not found", username);
                return null;
            }
            return trainee;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        if (userDao.checkCredentials(trainee.getUsername(), trainee.getPassword())) {
            log.info(CREDENTIAL_MATCH);
            return traineeDao.update(trainee);
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainee traineeToDelete = traineeDao.findByUsername(username);
            if (traineeToDelete == null) {
                log.warn("Trainee with username {} does not exist", username);
                return;
            }
            traineeDao.delete(traineeToDelete);
            log.info("Trainee with username: {} deleted", username);
        }
        log.warn(CREDENTIAL_MISMATCH);
    }

    @Override
    @Transactional
    public Trainee changePassword(String username, String currentPassword, String newPassword) {
        if (userDao.checkCredentials(username, currentPassword)) {
            log.info(CREDENTIAL_MATCH);
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setPassword(newPassword);
            log.info("Trainee password updated");
            return traineeDao.update(trainee);
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
    public List<Training> getTrainingsList(TrainingSearchCriteria criteria) {
        log.info("Returning trainings filtered by criteria: {}", criteria);
        return traineeDao.getTrainingsList(criteria);
    }


    private Boolean getaBoolean(String username, String password, boolean isActive) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setIsActive(isActive);
            log.info("Trainee={} isActive={}", username, isActive);
            return true;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return false;
    }
}
