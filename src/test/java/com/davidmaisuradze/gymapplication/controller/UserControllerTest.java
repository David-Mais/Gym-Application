package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testLogin() throws Exception {
        CredentialsDto credentialsDto = CredentialsDto.builder()
                .username("Davit.Maisuradze")
                .password("actuallyNewPass")
                .build();

        when(userService.login(any(CredentialsDto.class))).thenReturn(true);

        mockMvc.perform(get("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentialsDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testChangePassword() throws Exception {
        PasswordChangeDto passwordChangeDto = PasswordChangeDto
                .builder()
                .username("user123")
                .oldPassword("oldPass")
                .newPassword("newPass")
                .build();

        when(userService.changePassword(any(PasswordChangeDto.class))).thenReturn(true);

        mockMvc.perform(put("/api/v1/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordChangeDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
