package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Slf4j
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        try {
            User user = entityManager
                    .createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            log.info("User with username: {} returned", username);
            return user;
        } catch (Exception e) {
            log.error("Error finding user by username: {}", username, e);
            return null;
        }
    }

    @Override
    public User update(User user) {
        entityManager.merge(user);
        return user;
    }

    @Override
    @Transactional
    public User deleteByUsername(User username) {
        User user = entityManager
                .createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        entityManager.remove(user);
        return user;
    }
}
