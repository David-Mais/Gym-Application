package com.davidmaisuradze.gymapplication.daoimpl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    Logger logger = LoggerFactory.getLogger(TrainingDaoImpl.class);
    private Map<String, Training> trainingMap;

    @Autowired
    public void setTrainingMap(Map<String, Training> trainingMap) {
        this.trainingMap = trainingMap;
        logger.info("training map inserted successfully");
    }

    @Override
    public void create(Training training) {
        String name = training.getTrainingName();
        if (trainingMap.containsKey(name)) {
            logger.warn("Training already exists");
            return;
        }
        trainingMap.put(name, training);
    }

    @Override
    public Training select(String name) {
        if (!trainingMap.containsKey(name)) {
            logger.error("No such training exists");
            return null;
        }
        return trainingMap.get(name);
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(trainingMap.values());
    }
}
