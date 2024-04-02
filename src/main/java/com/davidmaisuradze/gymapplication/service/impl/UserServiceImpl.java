package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String NO_USER = "No user found with username: ";
    private final UserDao userDao;
    @Override
    public boolean login(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        try {
            return userDao.checkCredentials(username, credentialsDto.getPassword());
        } catch (Exception e) {
            throw new GymException(NO_USER + username, "404");
        }
    }

    @Override
    @Transactional
    public boolean changePassword(String username, PasswordChangeDto passwordChangeDto) {
        try {
            boolean checked = userDao.checkCredentials(username, passwordChangeDto.getOldPassword());

            if (checked) {
                UserEntity user = userDao.findByUsername(username);
                user.setPassword(passwordChangeDto.getNewPassword());
                userDao.update(user);
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new GymException(NO_USER + username, "404");
        }
    }
}
