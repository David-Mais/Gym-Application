package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private TrainerDao trainerDao;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
        log.info("TrainerDao injected");
    }

    @Override
    public void create(Trainer trainer) {
        trainerDao.create(trainer);
        log.info("Created trainer {}", trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainerDao.update(trainer);
        log.info("Updated trainer {}", trainer);
    }

    @Override
    public Trainer select(long id) {
        log.info("Selecting trainer with id: {}", id);
        return trainerDao.select(id);
    }

    @Override
    public List<Trainer> selectAll() {
        log.info("Selecting all trainers");
        return trainerDao.findAll();
    }
}
