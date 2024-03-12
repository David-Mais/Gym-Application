package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.entity.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class TrainingDaoImpl implements TrainingDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public Training create(Training training) {
        entityManager.persist(training);
        return training;
    }
}
