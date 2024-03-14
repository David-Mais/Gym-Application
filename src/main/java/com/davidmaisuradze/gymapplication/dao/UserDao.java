package com.davidmaisuradze.gymapplication.dao;

import java.util.List;

public interface UserDao {
    boolean checkCredentials(String username, String password);
    List<String> getAllUsernames();
}
