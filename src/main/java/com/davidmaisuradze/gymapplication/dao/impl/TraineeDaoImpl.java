package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Repository
@Slf4j
public class TraineeDaoImpl  implements TraineeDao {
    private Map<Long, Trainee> traineeMap;
    private final AtomicLong idGenerator = new AtomicLong();

    @Autowired
    public void setTraineeMap(@Qualifier("traineeStorage") Map<Long, Trainee> traineeMap) {
        this.traineeMap = traineeMap;
        log.info("Trainee map injected");
    }

    @Override
    public Trainee create(Trainee trainee) {
        if (checkUniqueUsername(trainee.getUsername())) {
            log.warn("Trainee {} already exists", trainee);
            return null;
        }
        Long id = idGenerator.incrementAndGet();
        trainee.setUserId(id);
        traineeMap.put(id, trainee);
        log.info("Created Trainee: {}", trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        Long id = trainee.getUserId();
        if (!traineeMap.containsKey(id)) {
            log.warn("Trainee {} does not exist", trainee);
            return null;
        }
        traineeMap.put(id, trainee);
        log.info("Trainee {} updated", trainee);
        return trainee;
    }

    @Override
    public void delete(Trainee trainee) {
        Long id = trainee.getUserId();
        if (!traineeMap.containsKey(id)) {
            log.warn("Trainee {} does not exist", trainee);
            return;
        }
        traineeMap.remove(id);
        log.info("Trainee with id {} removed", id);
    }

    @Override
    public Trainee findById(Long id) {
        if (!traineeMap.containsKey(id)) {
            log.warn("No trainee with id {}", id);
            return null;
        }
        log.info("Trainee with id {} returned", id);
        return traineeMap.get(id);
    }

    @Override
    public List<Trainee> findAll() {
        List<Trainee> trainees = new ArrayList<>(traineeMap.values());
        log.info("Returned all trainees {}", trainees);
        return trainees;
    }

    private boolean checkUniqueUsername(String username) {
        return traineeMap
                .values()
                .stream()
                .anyMatch(trainee -> trainee.getUsername().equals(username));
    }
}
