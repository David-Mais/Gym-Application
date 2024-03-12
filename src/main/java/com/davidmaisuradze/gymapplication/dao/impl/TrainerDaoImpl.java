package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Slf4j
public class TrainerDaoImpl implements TrainerDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public Trainer create(Trainer trainer) {
        entityManager.persist(trainer);
        return trainer;
    }

    @Override
    @Transactional
    public Trainer findByUsername(String username) {
        User user = getUserByUsername(username);
        return user.getTrainer();
    }

    @Override
    public Trainer update(Trainer trainer) {
        entityManager.merge(trainer);
        return trainer;
    }

    private User getUserByUsername(String username) {
        return entityManager
                .createQuery("select  u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
