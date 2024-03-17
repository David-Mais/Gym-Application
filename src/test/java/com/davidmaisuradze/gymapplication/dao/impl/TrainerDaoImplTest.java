package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
class TrainerDaoImplTest {
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainingDao trainingDao;
    @Autowired
    private TrainingTypeDao trainingTypeDao;

    @Test
    void createTrainer() {
        Trainer trainer = getTrainer("user");

        Trainer createdTrainer = trainerDao.create(trainer);
        Assertions.assertNotNull(createdTrainer);
        Assertions.assertEquals(trainer.getUsername(), createdTrainer.getUsername());
    }

    @Test
    void findByUsername() {
        String expectedUser = "user";
        Trainer trainer = getTrainer("user");

        trainerDao.create(trainer);

        Trainer foundTrainer = trainerDao.findByUsername(expectedUser);

        assertNotNull(foundTrainer);
        assertEquals(expectedUser, foundTrainer.getUsername());
    }

    @Test
    void updateTrainer() {
        Trainer trainer = getTrainer("user");

        trainerDao.create(trainer);

        String updatedUsername = "updated";
        trainer.setUsername(updatedUsername);

        Trainer updatedTrainer = trainerDao.update(trainer);
        assertNotNull(updatedTrainer);

        assertEquals(updatedUsername, updatedTrainer.getUsername());
    }

    @Test
    void getTrainersNotAssigned() {
        prepareTraining();
        List<Trainer> trainers = trainerDao.getTrainersNotAssigned("other");
        assertEquals(1, trainers.size());
    }

    private Trainer getTrainer(String username) {
        TrainingType trainingType =
                trainingTypeDao
                        .findTrainingTypeByName("box");
        return Trainer
                .builder()
                .firstName("First")
                .lastName("Last")
                .isActive(true)
                .username(username)
                .password("pass")
                .specialization(trainingType)
                .build();
    }

    private Trainee getTrainee(String username) {
        return Trainee
                .builder()
                .firstName("First")
                .lastName("Last")
                .isActive(true)
                .username(username)
                .password("pass")
                .dateOfBirth(LocalDate.parse("2001-01-01"))
                .build();
    }

    private void prepareTraining() {
        Trainer trainerOne = getTrainer("trainer1");
        Trainer trainerTwo = getTrainer("trainer2");
        trainerTwo.setFirstName("TrTwo");
        trainerTwo.setLastName("LstTwo");
        trainerDao.create(trainerOne);
        trainerDao.create(trainerTwo);

        Trainee traineeOne = getTrainee("some");
        Trainee traineeTwo = getTrainee("other");
        traineeDao.create(traineeOne);
        traineeDao.create(traineeTwo);

        TrainingType trainingType =
                trainingTypeDao.findTrainingTypeByName("box");

        Training trainingOne = Training
                .builder()
                .trainee(traineeOne)
                .trainer(trainerOne)
                .trainingName("name 1")
                .trainingType(trainingType)
                .trainingDate(LocalDate.parse("2000-01-01"))
                .duration(100)
                .build();

        Training trainingTwo = Training
                .builder()
                .trainee(traineeTwo)
                .trainer(trainerTwo)
                .trainingName("name 2")
                .trainingType(trainingType)
                .trainingDate(LocalDate.parse("2000-02-02"))
                .duration(60)
                .build();

        trainingDao.create(trainingOne);
        trainingDao.create(trainingTwo);
    }
}
