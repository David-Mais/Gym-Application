package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Sql(scripts = "/database/test-schema.sql")
@ActiveProfiles("test")
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogin_WhenCredentialsNotMatch_ThenReturnIsUnauthorized() throws Exception {
        CredentialsDto credentialsDto = CredentialsDto.builder()
                .username("Davit.Maisuradze15")
                .password("actuallyNewPass")
                .build();

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentialsDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testLogin_WhenCredentialsMatch_ThenReturnIsOk() throws Exception {
        CredentialsDto credentialsDto = CredentialsDto.builder()
                .username("Davit.Maisuradze")
                .password("newPass")
                .build();

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(credentialsDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testChangePassword_WhenCredentialsNotMatch_ThenReturnIsUnauthorized() throws Exception {
        PasswordChangeDto passwordChangeDto = PasswordChangeDto
                .builder()
                .username("Davit.Maisuradze")
                .oldPassword("somePass")
                .newPassword("newPass")
                .build();

        mockMvc.perform(put("/api/v1/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordChangeDto)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void testChangePassword_WhenCredentialsMatch_ThenReturnIsOk() throws Exception {
        PasswordChangeDto passwordChangeDto = PasswordChangeDto
                .builder()
                .username("Davit.Maisuradze")
                .oldPassword("newPass")
                .newPassword("somePass")
                .build();

        mockMvc.perform(put("/api/v1/users/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passwordChangeDto)))
                .andExpect(status().isOk());
    }
}
