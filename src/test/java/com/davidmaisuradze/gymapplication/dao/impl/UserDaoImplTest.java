package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.config.DataSourceConfig;
import com.davidmaisuradze.gymapplication.config.HibernateConfig;
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
@ContextConfiguration(classes = { DataSourceConfig.class, HibernateConfig.class, ApplicationConfig.class })
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
