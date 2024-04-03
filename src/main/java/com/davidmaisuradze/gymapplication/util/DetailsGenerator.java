package com.davidmaisuradze.gymapplication.util;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class DetailsGenerator {
    private final UserDao userDao;

    @Autowired
    public DetailsGenerator(UserDao userDao) {
        this.userDao = userDao;
    }

    public String generateUsername(String firstName, String lastName) {
        List<String> usernames = userDao.getAllUsernames();

        String baseUsername = firstName + "." + lastName;
        String finalUsername = baseUsername;
        int counter = 0;

        while (usernames.contains(finalUsername)) {
            counter++;
            finalUsername = baseUsername + counter;
        }

        log.info("Username generated: " + finalUsername);
        return finalUsername;
    }

    public String generatePassword() {
        log.info("Random password generated");
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
