package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Training> getTrainingsList(TrainerTrainingSearchDto criteria) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM Training t WHERE 1=1");

        Map<String, Object> parameters = new HashMap<>();

        if (criteria.getFrom() != null) {
            jpql.append(" AND t.trainingDate > :from");
            parameters.put("from", criteria.getFrom());
            log.info("From date criteria added");
        }
        if (criteria.getTo() != null) {
            jpql.append(" AND t.trainingDate < :to");
            parameters.put("to", criteria.getTo());
            log.info("To date criteria added");
        }
        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            jpql.append(" AND t.trainee.username = :traineeName");
            parameters.put("traineeName", criteria.getName());
            log.info("Name criteria added");
        }

        TypedQuery<Training> query = entityManager.createQuery(jpql.toString(), Training.class);
        parameters.forEach(query::setParameter);
        log.info("All parameters set");

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
