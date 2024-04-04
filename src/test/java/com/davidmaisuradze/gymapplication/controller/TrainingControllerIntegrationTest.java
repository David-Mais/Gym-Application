package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.config.WebMvcConfig;
import com.davidmaisuradze.gymapplication.dto.training.CreateTrainingDto;
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
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebMvcConfig.class, ApplicationConfig.class})
@Sql(scripts = "/gym-schema.sql")
class TrainingControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    void testCreateTraining() throws Exception {
        CreateTrainingDto createTrainingDto = CreateTrainingDto
                .builder()
                .traineeUsername(null)
                .trainerUsername("")
                .trainingName("training")
                .trainingDate(LocalDate.parse("2024-10-11"))
                .duration(60)
                .build();


        mockMvc.perform(post("/api/v1/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(createTrainingDto)))
                .andExpect(status().isBadRequest());
    }
}
