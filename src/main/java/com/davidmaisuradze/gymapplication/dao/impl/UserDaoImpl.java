package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.entity.User;
import jakarta.persistence.EntityManager;


//@Repository
public class UserDaoImpl implements UserDao {
    private EntityManager entityManager;
//    @Autowired
//    public UserDaoImpl(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    @Override
    public User create(User trainee) {
        return null;
    }

    @Override
    public User findByUsername(User username) {
        return null;
    }

    @Override
    public User update(User trainee) {
        return null;
    }

    @Override
    public User deleteByUsername(User username) {
        return null;
    }
}
