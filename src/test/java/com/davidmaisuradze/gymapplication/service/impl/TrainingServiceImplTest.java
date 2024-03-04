package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
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
        Training training = new Training(
                1111L,
                2222L,
                "spar",
                new TrainingType("Box"),
                LocalDate.parse("1992-05-13"),
                2
        );
        trainingService.crete(training);
        verify(trainingDao).create(training);
    }

    @Test
    void testSelect() {
        String id = "spar";
        Training training = new Training(
                1111L,
                2222L,
                "spar",
                new TrainingType("Box"),
                LocalDate.parse("1992-05-13"),
                2
        );
        when(trainingDao.select(id)).thenReturn(training);

        Training result = trainingService.select(id);

        verify(trainingDao).select(id);
        assertEquals(training, result);
    }

    @Test
    void testSelectAll() {
        Training training1 = new Training(
                1111L,
                2222L,
                "spar",
                new TrainingType("Box"),
                LocalDate.parse("1992-05-13"),
                2
        );
        Training training2 = new Training(
                3333L,
                4444L,
                "stretch",
                new TrainingType("Yoga"),
                LocalDate.parse("2005-02-20"),
                2
        );
        List<Training> expectedTrainers = Arrays.asList(training1, training2);
        when(trainingDao.findAll()).thenReturn(expectedTrainers);

        List<Training> result = trainingService.findAll();

        verify(trainingDao).findAll();
        assertEquals(expectedTrainers, result);
    }
}