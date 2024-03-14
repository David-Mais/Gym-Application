package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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
        try {
            String actualPassword = (String) entityManager
                    .createQuery("select u.password from UserEntity u where username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
            log.info("Checking credentials");
            return password.equals(actualPassword);
        }catch (NoResultException e) {
            log.warn(e.toString());
            return false;
        }
    }

    @Override
    public List<String> getAllUsernames() {
        return entityManager
                .createQuery("select u.username from UserEntity u", String.class)
                .getResultList();
    }
}
