package com.davidmaisuradze.gymapplication.daoimpl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.model.Trainee;
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
    Logger logger = LoggerFactory.getLogger(TraineeDaoImpl.class);
    private Map<Long, Trainee> traineeMap;

    @Autowired
    public void setTraineeMap(@Qualifier("traineeMap") Map<Long, Trainee> traineeMap) {
        this.traineeMap = traineeMap;
        logger.info("Trainee map inserted successfully");
    }

    @Override
    public void create(Trainee trainee) {
        long id = trainee.getUserId();
        if (traineeMap.containsKey(id)) {
            logger.warn("User already exists");
            return;
        }
        traineeMap.put(id, trainee);
    }

    @Override
    public void update(Trainee trainee) {
        long id = trainee.getUserId();
        if (!traineeMap.containsKey(id)) {
            logger.error("Trainee does not exist");
            return;
        }
        traineeMap.put(id, trainee);
    }

    @Override
    public void delete(Trainee trainee) {
        long id = trainee.getUserId();
        if (!traineeMap.containsKey(id)) {
            logger.error("Trainee does not exist");
            return;
        }
        traineeMap.remove(id);
    }

    @Override
    public Trainee select(long id) {
        if (!traineeMap.containsKey(id)) {
            logger.warn("No such user");
            return null;
        }
        return traineeMap.get(id);
    }

    @Override
    public List<Trainee> findAll() {
        List<Trainee> trainees = new ArrayList<>(traineeMap.values());
        logger.info("All Trainees list returned");
        return trainees;
    }
}
