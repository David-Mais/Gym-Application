package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.UserEntity;

import java.util.List;

public interface UserDao {
    boolean checkCredentials(String username, String password);
    UserEntity update(UserEntity user);
    UserEntity findByUsername(String username);
    List<String> getAllUsernames();
}
