package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainingtype.TrainingTypeDto;
import com.davidmaisuradze.gymapplication.service.TrainerService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class TrainerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
    }

    @Test
    void testCreateTrainer_WhenDtoIsProvided_ThenReturnIsCreated() throws Exception {
        CreateTrainerDto createTrainerDto = CreateTrainerDto.builder()
                .firstName("John")
                .lastName("Doe")
                .specialization(TrainingTypeDto.builder().trainingTypeName("box").build())
                .build();

        mockMvc.perform(post("/api/v1/trainers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper()
                                .registerModule(new JavaTimeModule())
                                .writeValueAsString(createTrainerDto)))
                        .andExpect(status().isCreated());
    }

    @Test
    void testUpdateTrainerProfile_WhenDtoIsProvided_ThenReturnIsOk() throws Exception {
        String username = "testUser";
        TrainerProfileUpdateRequestDto requestDto = new TrainerProfileUpdateRequestDto();
        requestDto.setFirstName("John");
        requestDto.setLastName("Doe");
        requestDto.setSpecialization(TrainingTypeDto.builder().trainingTypeName("grapple").build());
        requestDto.setIsActive(true);

        TrainerProfileUpdateResponseDto expectedResponse = new TrainerProfileUpdateResponseDto();

        when(trainerService.updateProfile(eq(username), any(TrainerProfileUpdateRequestDto.class)))
                .thenReturn(expectedResponse);

        mockMvc.perform(put("/api/v1/trainers/profile/{username}", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        verify(trainerService).updateProfile(eq(username), any(TrainerProfileUpdateRequestDto.class));
    }

    @Test
    void testGetTrainersNotAssigned_WhenUsernameExists_ThenReturnIsOk() throws Exception {
        String username = "testUser";
        List<TrainerInfoDto> expected = Arrays.asList(
                new TrainerInfoDto(),
                new TrainerInfoDto()
        );

        when(trainerService.getTrainersNotAssigned(username)).thenReturn(expected);

        mockMvc.perform(get("/api/v1/trainers/not-assigned/{username}", username)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(trainerService).getTrainersNotAssigned(username);
    }

    @Test
    void testGetProfile_WhenUsernameExists_ThenReturnIsOk() throws Exception{
        String username = "Davit.Maisuradze";

        when(trainerService.getProfile(username)).thenReturn(new TrainerProfileDto());

        mockMvc.perform(get("/api/v1/trainers/profile/{username}", username))
                .andExpect(status().isOk());
    }

    @Test
    void testActivateProfile_WhenUsernameExists_ThenReturnIsOk() throws Exception {
        String username = "Davit.Maisuradze";
        ActiveStatusDto statusDto = new ActiveStatusDto(true);

        mockMvc.perform(patch("/api/v1/trainers/{username}/active", username)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(statusDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeactivateProfile_WhenUsernameExists_ThenReturnIsOk() throws Exception {
        String username = "Davit.Maisuradze";
        ActiveStatusDto statusDto = new ActiveStatusDto(false);

        mockMvc.perform(patch("/api/v1/trainers/{username}/active", username)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(statusDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetTrainings_WhenUsernameExists_ThenReturnIsOk() throws Exception {
        String username = "Davit.Maisuradze";

        mockMvc.perform(get("/api/v1/trainers/profile/{username}/trainings", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
