package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.trainingtype.TrainingTypeDto;
import com.davidmaisuradze.gymapplication.service.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class TrainingTypeControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingTypeController controller;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    void testGetAll_ThenReturnIsOk() throws Exception {
        List<TrainingTypeDto> expectedList = Arrays.asList(
                new TrainingTypeDto(1L, "type1"),
                new TrainingTypeDto(2L, "type2")
        );
        when(trainingTypeService.findAll()).thenReturn(expectedList);

        mockMvc.perform(get("/api/v1/trainingtypes/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
}
