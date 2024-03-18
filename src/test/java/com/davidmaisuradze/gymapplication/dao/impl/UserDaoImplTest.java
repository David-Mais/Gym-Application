package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.config.DaoTestConfig;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Sql(scripts = "/gym-schema.sql")
@Transactional
class UserDaoImplTest {
    @Autowired
    private UserDao userDao;

    @Test
    void testFindAllUsernames() {
        List<String> usernames = userDao.getAllUsernames();
        assertNotNull(usernames);
        assertEquals(7, usernames.size());
    }
}
