package com.davidmaisuradze.gymapplication.service.util;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class Generator {
    private final UserDao userDao;

    @Autowired
    public Generator(UserDao userDao) {
        this.userDao = userDao;
    }

    public String generateUsername(String firstName, String lastname) {
        List<String> usernames = userDao.getAllUsernames();

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

    public String generatePassword() {
        log.info("Random password generated");
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
