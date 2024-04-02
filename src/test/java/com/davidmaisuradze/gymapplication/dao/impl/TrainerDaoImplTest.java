package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.config.DataSourceConfig;
import com.davidmaisuradze.gymapplication.config.HibernateConfig;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DataSourceConfig.class, HibernateConfig.class, ApplicationConfig.class })
@Sql(scripts = "/gym-schema.sql")
@Transactional
class TrainerDaoImplTest {
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private TrainingTypeDao trainingTypeDao;

    @Test
    void createTrainer() {
        Trainer trainer = getTrainer();

        Trainer createdTrainer = trainerDao.create(trainer);
        assertNotNull(createdTrainer);
        assertEquals(trainer.getUsername(), createdTrainer.getUsername());
    }

    @Test
    void findByUsername() {
        String expectedUser = "user";
        Trainer trainer = getTrainer();

        trainerDao.create(trainer);

        Trainer foundTrainer = trainerDao.findByUsername(expectedUser);

        assertNotNull(foundTrainer);
        assertEquals(expectedUser, foundTrainer.getUsername());
    }

    @Test
    void updateTrainer() {
        Trainer trainer = getTrainer();

        trainerDao.create(trainer);

        String updatedUsername = "updated";
        trainer.setUsername(updatedUsername);

        Trainer updatedTrainer = trainerDao.update(trainer);
        assertNotNull(updatedTrainer);

        assertEquals(updatedUsername, updatedTrainer.getUsername());
    }

    @Test
    void getTrainersNotAssigned() {
        //Could not set value of type trainer. PropertyAccessException
        List<Trainer> trainers = trainerDao.getTrainersNotAssigned("Davit.Maisuradze");
        assertNotNull(trainers);
    }

    @Test
    void findAll() {
        //same problem as in getTrainersNotAssigned() method
        List<Trainer> trainers = trainerDao.findAll();
        assertNotNull(trainers);
        assertEquals(3, trainers.size());
    }

    private Trainer getTrainer() {
        TrainingType trainingType =
                trainingTypeDao
                        .findTrainingTypeByName("box");
        return Trainer
                .builder()
                .firstName("First")
                .lastName("Last")
                .isActive(true)
                .username("user")
                .password("pass")
                .specialization(trainingType)
                .build();
    }

}
