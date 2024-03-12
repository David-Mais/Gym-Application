package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
@Slf4j
public class TraineeDaoImpl  implements TraineeDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Trainee create(Trainee trainee) {
        entityManager.persist(trainee);
        return trainee;
    }

    @Override
    public Trainee findByUsername(String username) {
        Trainee trainee = entityManager
                .createQuery("select t from Trainee t where t.username = :username", Trainee.class)
                .setParameter("username", username)
                .getSingleResult();
        if (trainee == null) {
            log.warn("Trainee with username: {} does not exits", username);
            return null;
        }
        log.info("Trainee with username: {} found", username);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        entityManager.merge(trainee);
        return trainee;
    }

    @Override
    public Trainee deleteByUsername(String username) {
        Trainee traineeToDelete = entityManager
                .createQuery("select t from Trainee t where t.username = :username", Trainee.class)
                .setParameter("username", username)
                .getSingleResult();
        if (traineeToDelete == null) {
            log.warn("Trainee with username: {} does not exits", username);
            return null;
        }
        entityManager.remove(traineeToDelete);
        log.info("Trainee with username: {} deleted", username);
        return traineeToDelete;
    }

    @Override
    public List<Training> getTrainingsList(Date from, Date to, String trainerName, TrainingType trainingType) {
        List<Training> trainings = entityManager
                .createQuery("select t from Training t", Training.class)
                .getResultList();
        return trainings
                .stream()
                .filter(t -> t.getTrainingDate().compareTo(from) > 0)
                .filter(t -> t.getTrainingDate().compareTo(to) < 0)
                .filter(t -> t.getTrainer().getFirstName().equals(trainerName))
                .filter(t -> t.getTrainingType().equals(trainingType))
                .toList();
    }
}
