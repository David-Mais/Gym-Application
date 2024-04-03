package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
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
public class TraineeDaoImpl  implements TraineeDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Trainee create(Trainee trainee) {
        entityManager.persist(trainee);
        log.info("Trainee: {} created successfully", trainee);
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
    public List<Training> getTrainingsList(String username, TrainingSearchCriteria criteria) {
        TrainingQueryBuilder queryBuilder = new TrainingQueryBuilder();

        queryBuilder.withTraineeUsername(username);
        queryBuilder.withFromDate(criteria.getFrom());
        queryBuilder.withToDate(criteria.getTo());
        queryBuilder.withTrainerName(criteria.getName());
        queryBuilder.withTrainingTypeName(criteria.getTrainingTypeName());

        TypedQuery<Training> query = entityManager.createQuery(
                queryBuilder.getJpql().toString(),
                Training.class
        );
        queryBuilder.getParameters().forEach(query::setParameter);

        log.info("Returning list of trainings filtered by {}", criteria);
        return query.getResultList();
    }

    @Override
    public List<Trainer> getAllTrainers(String username) {
        return entityManager
                .createQuery("select t.trainer from Training t where t.trainee.username = :username", Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }

    @Override
    public List<Trainee> findAll() {
        return entityManager
                .createQuery("select t from Trainee t", Trainee.class)
                .getResultList();
    }
}
