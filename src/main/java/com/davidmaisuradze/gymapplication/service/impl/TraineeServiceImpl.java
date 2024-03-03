package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeDao traineeDao;

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
        logger.info("Trainee DAO injected into TraineeService");
    }

    @Override
    public void create(Trainee trainee) {
        logger.info("Creating trainee: {}", trainee.getFirstName());
        traineeDao.create(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        logger.info("Updating trainee: {}", trainee.getFirstName());
        traineeDao.update(trainee);
    }

    @Override
    public void delete(Trainee trainee) {
        logger.info("deleting trainee: {}", trainee.getFirstName());
        traineeDao.delete(trainee);
    }

    @Override
    public Trainee select(long id) {
        logger.info("Selecting trainee with id: {}", id);
        return traineeDao.select(id);
    }

    @Override
    public List<Trainee> selectAll() {
        logger.info("Selecting all trainees");
        return traineeDao.findAll();
    }
}
