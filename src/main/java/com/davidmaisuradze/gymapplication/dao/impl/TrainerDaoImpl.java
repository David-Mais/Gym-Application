package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TrainerDaoImpl implements TrainerDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Trainer create(Trainer trainer) {
        entityManager.persist(trainer);
        log.info("Trainer: {} created successfully", trainer);
        return trainer;
    }

    @Override
    public Trainer findByUsername(String username) {
        Trainer trainer = entityManager
                .createQuery("select t from Trainer t where t.username = :username", Trainer.class)
                .setParameter("username", username)
                .getSingleResult();
        log.info("Trainer with username: {} found", username);
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        entityManager.merge(trainer);
        log.info("Trainer: {} updated successfully", trainer);
        return trainer;
    }

    @Override
    public List<Training> getTrainingsList(TrainingSearchCriteria criteria) {
        String jpql = "SELECT t FROM Training t WHERE t.trainingDate > :from AND t.trainingDate < :to " +
                "AND t.trainee.firstName = :traineeName";

        log.info("Fetching list of trainings filtered by {}", criteria);
        return entityManager.createQuery(jpql, Training.class)
                .setParameter("from", criteria.getFrom())
                .setParameter("to", criteria.getTo())
                .setParameter("traineeName", criteria.getName())
                .getResultList();
    }

    @Override
    public List<Trainer> getTrainersNotAssigned(String username) {
        log.info("Fetching trainers not assigned to: {}", username);
        return entityManager
                .createQuery("select t from Trainer t " +
                        "where t not in " +
                        "(select tr.trainer from Training tr " +
                        "where tr.trainee.username = :username) " +
                        "and t.isActive = true", Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public List<Trainer> findAll() {
        return entityManager.createQuery(
                "select t from Trainer t",
                Trainer.class
        ).getResultList();
    }

    @Override
    public List<Trainee> getAllTrainees(String username) {
        return entityManager
                .createQuery("select t.trainee from Training t where t.trainer.username = :username", Trainee.class)
                .setParameter("username", username)
                .getResultList();
    }
}
