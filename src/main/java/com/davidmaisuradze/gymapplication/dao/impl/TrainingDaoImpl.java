package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.entity.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class TrainingDaoImpl implements TrainingDao {
    private Map<String, Training> trainingMap;

    @Autowired
    public void setTrainingMap(@Qualifier("trainingStorage") Map<String, Training> trainingMap) {
        this.trainingMap = trainingMap;
        log.info("Training Map injected");
    }

    @Override
    public Training create(Training training) {
        String name = training.getTrainingName();
        if (trainingMap.containsKey(name)) {
            log.warn("Training already exists");
            return null;
        }
        trainingMap.put(name, training);
        log.info("Created Training {}", training);
        return training;
    }

    @Override
    public Training select(String name) {
        if (!trainingMap.containsKey(name)) {
            log.warn("No training with name {}", name);
            return null;
        }
        log.info("Returning training with name: {}", name);
        return trainingMap.get(name);
    }

    @Override
    public List<Training> findAll() {
        log.info("Returning all trainings");
        return new ArrayList<>(trainingMap.values());
    }
}
