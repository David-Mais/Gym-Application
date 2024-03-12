package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class TrainingTypeDaoImpl implements TrainingTypeDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public TrainingType create(TrainingType trainingType) {
        entityManager.persist(trainingType);
        return trainingType;
    }
}
