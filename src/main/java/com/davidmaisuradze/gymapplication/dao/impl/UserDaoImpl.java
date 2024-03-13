package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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
    public String generateUsername(String firstName, String lastname) {
        List<String> usernames = entityManager
                .createQuery("select u.username from UserEntity u", String.class)
                .getResultList();

        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(firstName).append(".").append(lastname);
        while (true) {
            int counterBefore = counter;
            for (String username : usernames) {
                if (username.contentEquals(builder)) {
                    counter++;
                }
            }
            if (counter != 0) {
                builder.setLength(0);
                builder.append(firstName);
                builder.append(".");
                builder.append(lastname);
                builder.append(counter);
            }
            if (counterBefore == counter) {
                break;
            }
        }
        log.info("Username generated");
        return builder.toString();
    }

    @Override
    public String generatePassword() {
        log.info("Random password generated");
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
