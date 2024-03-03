package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);
    private Map<Long, Trainer> trainerMap;

    @Autowired
    public void setTrainerMap(@Qualifier("trainerMap") Map<Long, Trainer> trainerMap) {
        this.trainerMap = trainerMap;
        logger.info("Trainer map injected");
    }

    @Override
    public void create(Trainer trainer) {
        long id = trainer.getUserId();
        if (trainerMap.containsKey(id)) {
            logger.warn("Trainer with id: {} already exists", id);
            return;
        }
        trainerMap.put(id, trainer);
        logger.info("Created trainer: {}", trainer);
    }

    @Override
    public void update(Trainer trainer) {
        long id = trainer.getUserId();
        if (!trainerMap.containsKey(id)) {
            logger.warn("No trainer found with id: {}", id);
            return;
        }
        trainerMap.put(id, trainer);
        logger.info("Trainer with id {} updated", id);
    }

    @Override
    public Trainer select(long id) {
        if (!trainerMap.containsKey(id)) {
            logger.info("No trainer with id: {}", id);
            return null;
        }
        logger.info("Returned trainer with id: {}", id);
        return trainerMap.get(id);
    }

    @Override
    public List<Trainer> findAll() {
        List<Trainer> trainees = new ArrayList<>(trainerMap.values());
        logger.info("Returning all trainers");
        return trainees;
    }
}
