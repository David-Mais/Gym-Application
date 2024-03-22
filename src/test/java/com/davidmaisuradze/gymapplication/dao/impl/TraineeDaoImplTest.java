package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.config.DaoTestConfig;
import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.dto.TrainingSearchCriteria;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Sql(scripts = "/gym-schema.sql")
@Transactional
class TraineeDaoImplTest {
    @Autowired
    private TraineeDao traineeDao;

    @Test
    void testCreate() {
        Trainee trainee = getTrainee();

        Trainee createdTrainee = traineeDao.create(trainee);
        assertNotNull(createdTrainee);
    }

    @Test
    void testFindByUsername() {
        Trainee trainee = getTrainee();
        traineeDao.create(trainee);

        Trainee fetchedTrainee = traineeDao.findByUsername("user");
        assertNotNull(fetchedTrainee);
    }

    @Test
    void testUpdate() {
        Trainee trainee = getTrainee();
        traineeDao.create(trainee);

        String updatedUsername = "updated";
        trainee.setUsername(updatedUsername);

        Trainee updatedTrainee = traineeDao.update(trainee);
        assertNotNull(updatedTrainee);

        assertEquals(updatedUsername, updatedTrainee.getUsername());
    }


    @Test
    void testDelete() {
        Trainee trainee = getTrainee();
        traineeDao.create(trainee);

        Trainee fetchedTrainee = traineeDao.findByUsername(trainee.getUsername());
        assertNotNull(fetchedTrainee);

        traineeDao.delete(trainee);

        assertThrows(NoResultException.class, () -> traineeDao.findByUsername(trainee.getUsername()));
    }

    @Test
    void testTrainingsByCriteria() {
        TrainingSearchCriteria criteria =
                TrainingSearchCriteria
                        .builder()
                        .from(LocalDate.parse("1990-01-01"))
                        .to(LocalDate.parse("2012-01-01"))
                        .build();
        List<Training> trainings = traineeDao.getTrainingsList(criteria);
        assertNotNull(trainings);

        assertEquals(2, trainings.size());
    }

    @Test
    void testFindAll() {
        List<Trainee> allTrainees = traineeDao.findAll();
        assertNotNull(allTrainees);
        assertEquals(4, allTrainees.size());
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
