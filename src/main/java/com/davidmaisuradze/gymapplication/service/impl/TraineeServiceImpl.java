package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.service.TraineeService;
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
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDao traineeDao;
    private final Generator generator;
    private final Authenticator authenticator;

    @Autowired
    public TraineeServiceImpl(
            TraineeDao traineeDao,
            Generator generator,
            Authenticator authenticator) {
        this.traineeDao = traineeDao;
        this.generator = generator;
        this.authenticator = authenticator;
    }

    @Override
    @Transactional
    public Trainee create(Trainee trainee) {
        String password = generator.generatePassword();
        String username = generator.generateUsername(
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
        if (authenticator.checkCredentials(username, password)) {
            return traineeDao.findByUsername(username);
        }
        return null;
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        if (authenticator.checkCredentials(trainee.getUsername(), trainee.getPassword())) {
            return traineeDao.update(trainee);
        }
        return null;
    }

    @Override
    @Transactional
    public Trainee deleteByUsername(String username, String password) {
        if (authenticator.checkCredentials(username, password)) {
            return traineeDao.deleteByUsername(username);
        }
        return null;
    }

    @Override
    @Transactional
    public Trainee changePassword(String username, String currentPassword, String newPassword) {
        if (authenticator.checkCredentials(username, currentPassword)) {
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setPassword(newPassword);
            return traineeDao.update(trainee);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean activate(String username, String password) {
        if (authenticator.checkCredentials(username, password)) {
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setActive(true);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deactivate(String username, String password) {
        if (authenticator.checkCredentials(username, password)) {
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setActive(false);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<Training> getTrainingsList(Date from, Date to, String trainerName, TrainingType trainingType) {
        return traineeDao.getTrainingsList(from, to, trainerName, trainingType);
    }
}
