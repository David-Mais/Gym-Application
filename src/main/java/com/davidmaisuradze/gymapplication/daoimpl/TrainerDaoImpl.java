package com.davidmaisuradze.gymapplication.daoimpl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.model.Trainer;
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
    Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);
    private Map<Long, Trainer> trainerMap;

    @Autowired
    public void setTrainerMap(@Qualifier("trainerMap") Map<Long, Trainer> trainerMap) {
        this.trainerMap = trainerMap;
        logger.info("Trainer Map Created Successfully");
    }

    @Override
    public void create(Trainer trainer) {
        long id = trainer.getUserId();
        if (trainerMap.containsKey(id)) {
            logger.warn("Trainer already exists");
            return;
        }
        trainerMap.put(id, trainer);
    }

    @Override
    public void update(Trainer trainer) {
        long id = trainer.getUserId();
        if (!trainerMap.containsKey(id)) {
            logger.error("Trainer does not exist");
            return;
        }
        trainerMap.put(id, trainer);
    }

    @Override
    public Trainer select(long id) {
        if (!trainerMap.containsKey(id)) {
            logger.warn("No such user");
            return null;
        }
        return trainerMap.get(id);
    }

    @Override
    public List<Trainer> findAll() {
        List<Trainer> trainees = new ArrayList<>(trainerMap.values());
        logger.info("Returning all trainers");
        return trainees;
    }
}
