package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.service.TraineeService;
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

    @Autowired
    public TraineeServiceImpl(
            TraineeDao traineeDao,
            UserDao userDao) {
        this.traineeDao = traineeDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public Trainee create(Trainee trainee) {
        String password = userDao.generatePassword();
        String username = userDao.generateUsername(
                trainee.getFirstName(),
                trainee.getLastName()
        );
        trainee.setPassword(password);
        trainee.setUsername(username);
        traineeDao.create(trainee);
        return trainee;
    }

    @Override
    @Transactional
    public Trainee findByUsername(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            Trainee trainee = traineeDao.findByUsername(username);
            if (trainee == null) {
                log.warn("Trainee with username: {} not found", username);
                return null;
            }
            return trainee;
        }
        log.warn("Username: {} and Password: {} does not match", username, password);
        return null;
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        if (userDao.checkCredentials(trainee.getUsername(), trainee.getPassword())) {
            return traineeDao.update(trainee);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            Trainee traineeToDelete = traineeDao.findByUsername(username);
            if (traineeToDelete == null) {
                log.warn("Trainee with username {} does not exist", username);
                return;
            }
            traineeDao.delete(traineeToDelete);
        }
    }

    @Override
    @Transactional
    public Trainee changePassword(String username, String currentPassword, String newPassword) {
        if (userDao.checkCredentials(username, currentPassword)) {
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setPassword(newPassword);
            return traineeDao.update(trainee);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean activate(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setIsActive(true);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deactivate(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setIsActive(false);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Training> getTrainingsList(TrainingSearchCriteria criteria) {
        return traineeDao.getTrainingsList(criteria);
    }
}
