package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.config.WebMvcConfig;
import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebMvcConfig.class, ApplicationConfig.class})
@Sql(scripts = "/gym-schema.sql")
class TraineeControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testRegister_WhenLastNameIsNull_ThenReturnIsBadRequest() throws Exception {
        CreateTraineeDto trainerDto = new CreateTraineeDto();
        trainerDto.setFirstName("John");
        trainerDto.setLastName(null);

        mockMvc.perform(post("/api/v1/trainees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainerDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void testRegister_WhenDtoIsProvided_ThenReturnIsCreated() throws Exception {
        CreateTraineeDto trainerDto = CreateTraineeDto
                .builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.parse("2000-01-01"))
                .build();

        mockMvc.perform(post("/api/v1/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(trainerDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetProfile_WhenUsernameNotExist_ThenReturnIsNotFound() throws Exception {
        String username = "Davit.Maisuradze3";

        mockMvc.perform(get("/api/v1/trainees/profile/{username}", username))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetProfile_WhenUsernameExists_ThenReturnIsOk() throws Exception {
        String username = "Davit.Maisuradze";

        mockMvc.perform(get("/api/v1/trainees/profile/{username}", username))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProfile_WhenUsernameNotExists_ThenReturnIsNotFound() throws Exception {
        String username = "Davit.Maisuradze2";

        mockMvc.perform(delete("/api/v1/trainees/profile/{username}", username))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void testDeleteProfile_WhenUsernameExists_ThenReturnIsNoContent() throws Exception {
        String username = "Davit.Maisuradze";

        mockMvc.perform(delete("/api/v1/trainees/profile/{username}", username))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActiveStatus_WhenUsernameNotExists_ThenReturnIsNotFound() throws Exception {
        String username = "Davit.Maisuradze10";
        ActiveStatusDto statusDto = new ActiveStatusDto(false);

        mockMvc.perform(patch("/api/v1/trainees/{username}/active", username)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(statusDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void testActiveStatus_WhenUsernameExists_ThenReturnIsOk() throws Exception {
        String username = "Davit.Maisuradze";
        ActiveStatusDto statusDto = new ActiveStatusDto(true);

        mockMvc.perform(patch("/api/v1/trainees/{username}/active", username)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(statusDto)))
                .andExpect(status().isOk());
    }

    @Test
    void TestGetTrainings_WhenUsernameNotExists_ThenReturnIsNotFound() throws Exception {
        String username = "Davit.Maisuradze8";

        mockMvc.perform(get("/api/v1/trainees/profile/{username}/trainings", username))
                .andExpect(status().isNotFound());
    }

    @Test
    void TestGetTrainings_WhenUsernameExists_ThenReturnIsOk() throws Exception {
        String username = "Davit.Maisuradze";

        mockMvc.perform(get("/api/v1/trainees/profile/{username}/trainings", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
