package com.davidmaisuradze.gymapplication.dao;

public interface UserDao {
    boolean checkCredentials(String username, String password);
    String generateUsername(String firstName, String lastname);
    String generatePassword();
}
