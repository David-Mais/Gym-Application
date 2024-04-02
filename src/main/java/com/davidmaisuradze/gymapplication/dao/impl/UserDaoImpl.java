package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean checkCredentials(String username, String password) {
        String actualPassword = (String) entityManager
                .createQuery("select u.password from UserEntity u where u.username = :username")
                .setParameter("username", username)
                .getSingleResult();
        log.info("Checking credentials");
        return password.equals(actualPassword);
    }

    @Override
    public UserEntity update(UserEntity user) {
        entityManager.merge(user);
        return user;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return entityManager
                .createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<String> getAllUsernames() {
        log.info("Fetching all usernames");
        return entityManager
                .createQuery("select u.username from UserEntity u", String.class)
                .getResultList();
    }
}
