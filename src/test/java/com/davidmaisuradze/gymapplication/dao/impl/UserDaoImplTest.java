package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
class UserDaoImplTest {
    @Autowired
    private TrainingTypeDao trainingTypeDao;
    @Autowired
    private TraineeDao traineeDao;
    @Autowired
    private TrainerDao trainerDao;
    @Autowired
    private UserDao userDao;

    @Test
    void getAllUsernames() {
        Trainer trainer = getTrainer("trainer1");
        trainerDao.create(trainer);
        Trainer trainer2 = getTrainer("trainer2");
        trainerDao.create(trainer2);
        Trainer trainer3 = getTrainer("trainer3");
        trainerDao.create(trainer3);

        Trainee trainee = getTrainee("trainee1");
        traineeDao.create(trainee);
        Trainee trainee2 = getTrainee("trainee2");
        traineeDao.create(trainee2);
        Trainee trainee3 = getTrainee("trainee3");
        traineeDao.create(trainee3);

        List<String> usernames = userDao.getAllUsernames();

        //in sql inserts 7 users were created so we subtract 7 in test
        assertEquals(6, usernames.size() - 7);
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
}
