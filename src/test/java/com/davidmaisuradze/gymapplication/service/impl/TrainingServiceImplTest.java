package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {
    @Mock
    private TrainingDao trainingDao;
    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    void testCreate() {
        Training training = generateTraining(1L, 2L);
        trainingService.crete(training);
        verify(trainingDao).create(training);
    }

    @Test
    void testSelect() {
        String id = "spar";
        Training training = generateTraining(1L, 2L);
        when(trainingDao.findByName(id)).thenReturn(training);

        Training result = trainingService.findByName(id);

        verify(trainingDao).findByName(id);
        assertEquals(training, result);
    }

    @Test
    void testSelectAll() {
        Training training1 = generateTraining(1L, 2L);
        Training training2 = generateTraining(3L, 4L);
        List<Training> expectedTrainers = Arrays.asList(training1, training2);
        when(trainingDao.findAll()).thenReturn(expectedTrainers);

        List<Training> result = trainingService.findAll();

        verify(trainingDao).findAll();
        assertEquals(expectedTrainers, result);
    }

    private Training generateTraining(Long id1, Long id2) {
        return Training
                .builder()
                .traineeId(id1)
                .trainerId(id2)
                .trainingName("spar")
                .trainingType(TrainingType.builder().trainingTypeName("Box").build())
                .trainingDate(LocalDate.parse("1992-05-13"))
                .duration(2.0)
                .build();
    }
}