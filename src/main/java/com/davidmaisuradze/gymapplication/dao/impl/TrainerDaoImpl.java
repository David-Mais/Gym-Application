package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
@Slf4j
public class TrainerDaoImpl implements TrainerDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Trainer create(Trainer trainer) {
        entityManager.persist(trainer);
        return trainer;
    }

    @Override
    public Trainer findByUsername(String username) {
        Trainer trainer = entityManager
                .createQuery("select t from Trainer t where t.username = :username", Trainer.class)
                .setParameter("username", username)
                .getSingleResult();
        if (trainer == null) {
            log.warn("Trainer with username: {} does not exits", username);
            return null;
        }
        log.info("Trainer with username: {} found", username);
        return trainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        entityManager.merge(trainer);
        return trainer;
    }

    @Override
    public List<Training> getTrainingsList(Date from, Date to, String traineeName) {
        List<Training> trainings = entityManager
                .createQuery("select t from Training t", Training.class)
                .getResultList();
        return trainings
                .stream()
                .filter(t -> t.getTrainingDate().compareTo(from) > 0)
                .filter(t -> t.getTrainingDate().compareTo(to) < 0)
                .filter(t -> t.getTrainee().getFirstName().equals(traineeName))
                .toList();
    }

    @Override
    public List<Trainer> getTrainersNotAssigned(String username) {
        return entityManager
                .createQuery("select t from Trainer t " +
                        "where t not in " +
                        "(select tr.trainer from Training tr " +
                        "where tr.trainee.username = :username)", Trainer.class)
                .setParameter("username", username)
                .getResultList();
    }
}
