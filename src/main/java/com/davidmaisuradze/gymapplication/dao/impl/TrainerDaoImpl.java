package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
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
public class TrainerDaoImpl implements TrainerDao {
    private Map<Long, Trainer> trainerMap;
    private final AtomicLong atomicLong = new AtomicLong();

    @Autowired
    public void setTrainerMap(@Qualifier("trainerStorage") Map<Long, Trainer> trainerMap) {
        this.trainerMap = trainerMap;
        log.info("Trainer map injected");
    }

    @Override
    public Trainer create(Trainer trainer) {
        if (checkUniqueUsername(trainer.getUsername())) {
            log.warn("Trainer {} already exists", trainer);
            return null;
        }
        Long id = atomicLong.incrementAndGet();
        trainer.setUserId(id);
        trainerMap.put(id, trainer);
        log.info("Created trainer: {}", trainer);
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        Long id = trainer.getUserId();
        if (!trainerMap.containsKey(id)) {
            log.warn("No trainer found with id: {}", id);
            return null;
        }
        trainerMap.put(id, trainer);
        log.info("Trainer with id {} updated", id);
        return trainer;
    }

    @Override
    public Trainer findById(Long id) {
        if (!trainerMap.containsKey(id)) {
            log.warn("No trainer with id: {}", id);
            return null;
        }
        log.info("Returned trainer with id: {}", id);
        return trainerMap.get(id);
    }

    @Override
    public List<Trainer> findAll() {
        List<Trainer> trainees = new ArrayList<>(trainerMap.values());
        log.info("Returned all trainers");
        return trainees;
    }

    private boolean checkUniqueUsername(String username) {
        return trainerMap
                .values()
                .stream()
                .anyMatch(trainer -> trainer.getUsername().equals(username));
    }
}
