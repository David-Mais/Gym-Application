package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class TraineeDaoImpl  implements TraineeDao {
    private static final Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);
    private Map<Long, Trainee> traineeMap;

    @Autowired
    public void setTraineeMap(@Qualifier("traineeMap") Map<Long, Trainee> traineeMap) {
        this.traineeMap = traineeMap;
        logger.info("Trainee map injected");
    }

    @Override
    public void create(Trainee trainee) {
        long id = trainee.getUserId();
        if (traineeMap.containsKey(id)) {
            logger.warn("Trainee already exists");
            return;
        }
        traineeMap.put(id, trainee);
        logger.info("Created Trainee: {}", trainee);
    }

    @Override
    public void update(Trainee trainee) {
        long id = trainee.getUserId();
        if (!traineeMap.containsKey(id)) {
            logger.warn("Trainee does not exist");
            return;
        }
        traineeMap.put(id, trainee);
        logger.info("Trainee {} updated", trainee);
    }

    @Override
    public void delete(Trainee trainee) {
        long id = trainee.getUserId();
        if (!traineeMap.containsKey(id)) {
            logger.warn("Trainee {} does not exist", trainee);
            return;
        }
        traineeMap.remove(id);
        logger.info("Trainee with id {} removed", id);
    }

    @Override
    public Trainee select(long id) {
        if (!traineeMap.containsKey(id)) {
            logger.warn("No such trainee");
            return null;
        }
        logger.info("Trainee with id {} returned", id);
        return traineeMap.get(id);
    }

    @Override
    public List<Trainee> findAll() {
        List<Trainee> trainees = new ArrayList<>(traineeMap.values());
        logger.info("Returned all trainees");
        return trainees;
    }
}
