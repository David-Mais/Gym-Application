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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
class TrainingDaoImplTest {
    @Autowired
    private TrainingTypeDao trainingTypeDao;
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainingDao trainingDao;
    @Test
    void createTraining() {
        Training training = getTraining();
        Training createdTraining = trainingDao.create(training);
        assertNotNull(createdTraining);
        assertEquals(training.getTrainingName(), createdTraining.getTrainingName());
    }

    private Training getTraining() {
        TrainingType trainingType =
                trainingTypeDao.findTrainingTypeByName("box");
        Trainer trainer = Trainer
                .builder()
                .firstName("First")
                .lastName("Last")
                .isActive(true)
                .username("trainer")
                .password("pass")
                .specialization(trainingType)
                .build();
        trainerDao.create(trainer);

        Trainee trainee = Trainee
                .builder()
                .firstName("First")
                .lastName("Last")
                .isActive(true)
                .username("trainee")
                .password("pass")
                .dateOfBirth(LocalDate.parse("2001-01-01"))
                .build();
        traineeDao.create(trainee);

        return Training
                .builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingName("name 1")
                .trainingType(trainingType)
                .trainingDate(LocalDate.parse("2000-01-01"))
                .duration(100)
                .build();
    }
}
