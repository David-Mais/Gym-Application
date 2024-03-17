package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
class TraineeDaoImplTest {
    @Autowired
    private TraineeDao traineeDao;

    @Test
    void createTrainee() {
        Trainee trainee = Trainee
                .builder()
                .firstName("First")
                .lastName("Last")
                .isActive(true)
                .username("user")
                .password("pass")
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .build();

        Trainee savedTrainee = traineeDao.create(trainee);
        assertNotNull(savedTrainee);
    }

    @Test
    void findByUsername() {
        String expectedUser = "user";
        Trainee trainee = getTrainee();

        traineeDao.create(trainee);

        Trainee foundTrainee = traineeDao.findByUsername(expectedUser);

        assertNotNull(foundTrainee);
        assertEquals(expectedUser, foundTrainee.getUsername());
    }

    @Test
    void updateTrainee() {
        Trainee trainee = getTrainee();

        traineeDao.create(trainee);

        String updatedUsername = "updated";
        trainee.setUsername(updatedUsername);

        Trainee updatedTrainee = traineeDao.update(trainee);
        assertNotNull(updatedTrainee);

        assertEquals(updatedUsername, updatedTrainee.getUsername());
    }

    @Test
    void deleteTrainee() {
        Trainee trainee = getTrainee();
        traineeDao.create(trainee);

        Trainee fetchedTrainee = traineeDao.findByUsername(trainee.getUsername());
        assertNotNull(fetchedTrainee);

        traineeDao.delete(trainee);


        assertThrows(NoResultException.class, () -> traineeDao.findByUsername(trainee.getUsername()));
    }


    private Trainee getTrainee() {
        return Trainee
                .builder()
                .firstName("First")
                .lastName("Last")
                .isActive(true)
                .username("user")
                .password("pass")
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .build();
    }
}
