package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TrainingType findTrainingTypeByName(String name) {
        return entityManager
                .createQuery("select t from TrainingType t where t.trainingTypeName = :name", TrainingType.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<TrainingType> findAll() {
        return entityManager
                .createQuery("SELECT t from TrainingType t", TrainingType.class)
                .getResultList();
    }
}
