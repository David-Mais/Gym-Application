package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Slf4j
public class TraineeDaoImpl  implements TraineeDao {

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public Trainee create(Trainee trainee) {
        entityManager.persist(trainee);
        return trainee;
    }

    @Override
    @Transactional
    public Trainee findByUsername(String username) {
        User user = getUserByUsername(username);
        return user.getTrainee();
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        entityManager.merge(trainee);
        return trainee;
    }

    @Override
    @Transactional
    public Trainee deleteByUsername(String username) {
        User user = getUserByUsername(username);
        Trainee trainee = user.getTrainee();
        entityManager.remove(trainee);
        entityManager.remove(user);
        return trainee;
    }

    private User getUserByUsername(String username) {
        return entityManager
                .createQuery("select  u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
