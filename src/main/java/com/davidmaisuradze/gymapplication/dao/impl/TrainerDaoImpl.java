package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.util.TrainingQueryBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TrainerDaoImpl implements TrainerDao {
    @PersistenceContext
    private EntityManager entityManager;
    private static final String USERNAME = "username";
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
                .setParameter(USERNAME, username)
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
    public List<Training> getTrainingsList(String username, TrainerTrainingSearchDto criteria) {
        TrainingQueryBuilder queryBuilder = new TrainingQueryBuilder();
        queryBuilder.withTrainerUsername(username);
        queryBuilder.withFromDate(criteria.getFrom());
        queryBuilder.withToDate(criteria.getTo());
        queryBuilder.withTraineeName(criteria.getName());

        TypedQuery<Training> query = entityManager.createQuery(
                queryBuilder.getJpql().toString(),
                Training.class
        );
        queryBuilder.getParameters().forEach(query::setParameter);


        log.info("Returning list of trainings filtered by {}", criteria);
        return query.getResultList();
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
                .setParameter(USERNAME, username)
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
                .setParameter(USERNAME, username)
                .getResultList();
    }
}
