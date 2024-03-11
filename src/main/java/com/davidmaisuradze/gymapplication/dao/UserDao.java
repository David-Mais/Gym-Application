package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.User;

public interface UserDao {
    User create(User trainee);
    User findByUsername(User username);
    User update(User trainee);
    User deleteByUsername(User username);
}
