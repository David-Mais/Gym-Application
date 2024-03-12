package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.User;

public interface UserDao {
    User create(User user);
    User findByUsername(String username);
    User update(User user);
    User deleteByUsername(User username);
}
