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
    public void crete(Training training) {
        log.info("Creating training: {}", training.getTrainingName());
        trainingDao.create(training);
    }

    @Override
    public Training select(String name) {
        log.info("Selecting training with name: {}", name);
        return trainingDao.select(name);
    }

    @Override
    public List<Training> findAll() {
        log.info("Selecting all trainings");
        return trainingDao.findAll();
    }
}
