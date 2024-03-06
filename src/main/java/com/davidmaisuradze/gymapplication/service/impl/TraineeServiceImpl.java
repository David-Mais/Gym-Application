package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private TraineeDao traineeDao;

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
        log.info("Trainee DAO injected into TraineeService");
    }

    @Override
    public Trainee create(Trainee trainee) {
        log.info("Creating trainee: {}", trainee.getFirstName());
        traineeDao.create(trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        log.info("Updating trainee: {}", trainee.getFirstName());
        traineeDao.update(trainee);
        return trainee;
    }

    @Override
    public void delete(Trainee trainee) {
        log.info("deleting trainee: {}", trainee.getFirstName());
        traineeDao.delete(trainee);
    }

    @Override
    public Trainee findById(Long id) {
        log.info("Selecting trainee with id: {}", id);
        return traineeDao.findById(id);
    }

    @Override
    public List<Trainee> selectAll() {
        log.info("Selecting all trainees");
        return traineeDao.findAll();
    }
}
