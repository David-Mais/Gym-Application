package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.model.TrainingSearchCriteria;
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
    public List<Training> getTrainingsList(TrainingSearchCriteria criteria) {
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
            jpql.append(" AND t.trainer.firstName = :trainerName");
            parameters.put("trainerName", criteria.getName());
            log.info("Name criteria added");
        }
        if (criteria.getTrainingType() != null) {
            jpql.append(" AND t.trainingType = :trainingType");
            parameters.put("trainingType", criteria.getTrainingType());
            log.info("Training Type criteria added");
        }

        TypedQuery<Training> query = entityManager.createQuery(jpql.toString(), Training.class);
        parameters.forEach(query::setParameter);
        log.info("All parameters set");

        log.info("Returning list of trainings filtered by {}", criteria);
        return query.getResultList();
    }
}
