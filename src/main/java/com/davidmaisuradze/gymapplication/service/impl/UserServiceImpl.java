package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.service.UserService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String INVALID_CREDENTIALS = "Invalid Credentials";
    private final UserDao userDao;

    @Override
    public boolean login(CredentialsDto credentialsDto) {
        return loginHelper(
                credentialsDto.getUsername(),
                credentialsDto.getPassword()
        );
    }

    @Override
    @Transactional
    public boolean changePassword(PasswordChangeDto passwordChangeDto) {
        String username = passwordChangeDto.getUsername();
        String oldPassword = passwordChangeDto.getOldPassword();
        String newPassword = passwordChangeDto.getNewPassword();

        loginHelper(username, oldPassword);

        UserEntity user = userDao.findByUsername(username);
        user.setPassword(newPassword);
        userDao.update(user);
        return true;
    }

    private String checkedUsername(String username) {
        try {
            userDao.findByUsername(username);
            return username;
        } catch (NoResultException e) {
            throw new GymException(INVALID_CREDENTIALS, "401");
        }
    }

    private boolean loginHelper(String username, String password) {
        boolean checked = userDao.checkCredentials(
                checkedUsername(username),
                password
        );

        if (!checked) {
            throw new GymException(INVALID_CREDENTIALS, "401");
        }

        return true;
    }
}
