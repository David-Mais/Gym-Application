package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.training.CreateTrainingDto;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private TrainingController controller;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void testCreateTraining_WhenDtoIsProvided_thenReturnIsOk() throws Exception {
        CreateTrainingDto createTrainingDto = CreateTrainingDto
                .builder()
                .traineeUsername("user")
                .trainerUsername("trainer")
                .trainingName("training")
                .trainingDate(LocalDate.parse("2024-10-11"))
                .duration(60)
                .build();

        doNothing().when(trainingService).create(any(CreateTrainingDto.class));

        mockMvc.perform(post("/api/v1/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                        .registerModule(new JavaTimeModule())
                                        .writeValueAsString(createTrainingDto)))
                .andExpect(status().isOk());

        Mockito.verify(trainingService).create(any(CreateTrainingDto.class));
    }
}
