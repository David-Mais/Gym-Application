package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.repository.UserRepository;
import com.davidmaisuradze.gymapplication.service.UserService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String INVALID_CREDENTIALS = "Invalid Credentials";
    private final UserRepository userRepository;

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

        UserEntity user = userRepository.findByUsername(checkedUsername(username));
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    private String checkedUsername(String username) {
        try {
            userRepository.findByUsername(username);
            return username;
        } catch (NoResultException e) {
            throw new GymException(INVALID_CREDENTIALS, "401");
        }
    }

    private boolean loginHelper(String username, String password) {
        boolean checked = userRepository.findPasswordByUsername(username)
                .map(password::equals)
                .orElse(false);

        if (!checked) {
            throw new GymException(INVALID_CREDENTIALS, "401");
        }

        return true;
    }
}
