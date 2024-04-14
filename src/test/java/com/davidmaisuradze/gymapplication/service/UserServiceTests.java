package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.repository.UserRepository;
import com.davidmaisuradze.gymapplication.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testLogin() {
        CredentialsDto credentials = new CredentialsDto();
        credentials.setUsername("username");
        credentials.setPassword("password");

        when(userRepository.findPasswordByUsername(anyString())).thenReturn(Optional.of("password"));

        boolean result = userService.login(credentials);

        assertTrue(result);
    }


    @Test
    void testChangePassword() {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto();
        passwordChangeDto.setUsername("user");
        passwordChangeDto.setOldPassword("pass");
        passwordChangeDto.setNewPassword("newPass");

        UserEntity user = new Trainee();
        user.setUsername("user");
        user.setPassword("pass");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userRepository.findPasswordByUsername(anyString())).thenReturn(Optional.of("pass"));

        boolean result = userService.changePassword(passwordChangeDto);

        assertTrue(result);
        assertEquals("newPass", user.getPassword());

        verify(userRepository, times(1)).save(user);
    }
}
