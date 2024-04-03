package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class TraineeControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
    }

    @Test
    void testCreateTrainee() throws Exception {
        CreateTraineeDto createTraineeDto = CreateTraineeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        mockMvc.perform(post("/api/v1/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(createTraineeDto)))
                        .andExpect(status().isCreated());
    }

    @Test
    //could not write. it always returns code 201 even if i dont provide validated fields
    void testCreateTraineeMissingRequiredFields() throws Exception {
        CreateTraineeDto createTraineeDtoMissingFields = CreateTraineeDto.builder()
                .firstName(null)
                .lastName(null)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        mockMvc.perform(post("/api/v1/trainees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(createTraineeDtoMissingFields)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testGetProfile() throws Exception{
        String username = "Davit.Maisuradze";

        when(traineeService.getProfile(username)).thenReturn(new TraineeProfileDto());

        mockMvc.perform(get("/api/v1/trainees/profile/{username}", username))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProfile() throws Exception {
        String username = "Davit.Maisuradze";

        mockMvc.perform(delete("/api/v1/trainees/profile/{username}", username))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActivateProfile() throws Exception {
        String username = "Davit.Maisuradze";
        ActiveStatusDto statusDto = new ActiveStatusDto(true);

        mockMvc.perform(patch("/api/v1/trainees/{username}/active", username)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(statusDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeactivateProfile() throws Exception {
        String username = "Davit.Maisuradze";
        ActiveStatusDto statusDto = new ActiveStatusDto(false);

        mockMvc.perform(patch("/api/v1/trainees/{username}/active", username)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(statusDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTrainings() throws Exception {
        String username = "Davit.Maisuradze";

        mockMvc.perform(get("/api/v1/trainees/profile/{username}/trainings", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}
