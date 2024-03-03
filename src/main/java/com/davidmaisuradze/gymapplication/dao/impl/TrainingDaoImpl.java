package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.entity.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);
    private Map<String, Training> trainingMap;

    @Autowired
    public void setTrainingMap(Map<String, Training> trainingMap) {
        this.trainingMap = trainingMap;
        logger.info("Training Map injected");
    }

    @Override
    public void create(Training training) {
        String name = training.getTrainingName();
        if (trainingMap.containsKey(name)) {
            logger.warn("Training already exists");
            return;
        }
        trainingMap.put(name, training);
        logger.info("Created Training {}", training);
    }

    @Override
    public Training select(String name) {
        if (!trainingMap.containsKey(name)) {
            logger.warn("No training with name {}", name);
            return null;
        }
        logger.info("Returning training with name: {}", name);
        return trainingMap.get(name);
    }

    @Override
    public List<Training> findAll() {
        logger.info("Returning all trainings");
        return new ArrayList<>(trainingMap.values());
    }
}
