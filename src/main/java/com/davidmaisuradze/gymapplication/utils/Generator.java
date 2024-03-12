package com.davidmaisuradze.gymapplication.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class Generator {
    @PersistenceContext
    private EntityManager entityManager;

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
        return builder.toString();
    }

    public String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
