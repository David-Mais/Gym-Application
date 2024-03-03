package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    private TrainerDao trainerDao;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
        logger.info("TrainerDao injected");
    }

    @Override
    public void create(Trainer trainer) {
        trainerDao.create(trainer);
        logger.info("Created trainer {}", trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainerDao.update(trainer);
        logger.info("Updated trainer {}", trainer);
    }

    @Override
    public Trainer select(long id) {
        logger.info("Selecting trainer with id: {}", id);
        return trainerDao.select(id);
    }

    @Override
    public List<Trainer> selectAll() {
        logger.info("Selecting all trainers");
        return trainerDao.findAll();
    }
}
