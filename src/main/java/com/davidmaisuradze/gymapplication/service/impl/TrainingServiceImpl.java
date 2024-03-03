package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
        logger.info("Training DAO injected into TrainingService");
    }

    @Override
    public void crete(Training training) {
        logger.info("Creating training: {}", training.getTrainingName());
        trainingDao.create(training);
    }

    @Override
    public Training select(String name) {
        logger.info("Selecting training with name: {}", name);
        return trainingDao.select(name);
    }

    @Override
    public List<Training> findAll() {
        logger.info("Selecting all trainings");
        return trainingDao.findAll();
    }
}
