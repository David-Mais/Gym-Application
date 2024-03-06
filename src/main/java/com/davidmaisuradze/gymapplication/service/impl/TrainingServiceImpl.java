package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
        log.info("Training DAO injected into TrainingService");
    }

    @Override
    public Training crete(Training training) {
        log.info("Creating training: {}", training.getTrainingName());
        trainingDao.create(training);
        return training;
    }

    @Override
    public Training findByName(String name) {
        log.info("Selecting training with name: {}", name);
        return trainingDao.findByName(name);
    }

    @Override
    public List<Training> findAll() {
        log.info("Selecting all trainings");
        return trainingDao.findAll();
    }
}
