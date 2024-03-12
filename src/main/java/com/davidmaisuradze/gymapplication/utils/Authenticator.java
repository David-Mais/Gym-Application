package com.davidmaisuradze.gymapplication.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Authenticator {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean checkCredentials(String username, String password) {
        try {
            String actualPassword = (String) entityManager
                    .createQuery("select u.password from UserEntity u where username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
            return password.equals(actualPassword);
        }catch (NoResultException e) {
            log.warn(e.toString());
            return false;
        }
    }
}
