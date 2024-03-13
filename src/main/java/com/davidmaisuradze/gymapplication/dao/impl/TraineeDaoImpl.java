package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingSearchCriteria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
        log.info("Trainee with username: {} found", username);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        entityManager.merge(trainee);
        log.info("Trainee: {} updated successfully", trainee);
        return trainee;
    }

    @Override
    public void delete(Trainee trainee) {
        entityManager.remove(trainee);
        log.info("Trainee {} deleted", trainee);
    }

    @Override
    public List<Training> getTrainingsList(TrainingSearchCriteria criteria) {
        String jpql = "SELECT t FROM Training t WHERE t.trainingDate > :from AND t.trainingDate < :to " +
                "AND t.trainer.firstName = :trainerName AND t.trainingType = :trainingType";

        log.info("Returning list of trainings filtered by {}", criteria);
        return entityManager.createQuery(jpql, Training.class)
                .setParameter("from", criteria.getFrom())
                .setParameter("to", criteria.getTo())
                .setParameter("trainerName", criteria.getName())
                .setParameter("trainingType", criteria.getTrainingType())
                .getResultList();
    }
}
