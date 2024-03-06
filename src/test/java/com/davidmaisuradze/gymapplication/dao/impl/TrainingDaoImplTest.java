package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainingDaoImplTest {
    @Mock
    private Map<String, Training> trainingMap;

    @InjectMocks
    private TrainingDaoImpl trainingDao;

    @Test
    void testCreateNewTraining() {
        Training training = generateTraining(1111L, 2222L);
        when(trainingMap.containsKey(training.getTrainingName())).thenReturn(false);

        trainingDao.create(training);

        verify(trainingMap, times(1)).put(training.getTrainingName(), training);
    }

    @Test
    void testCreateExistingTraining() {
        Training training = generateTraining(1111L, 2222L);
        when(trainingMap.containsKey(training.getTrainingName())).thenReturn(true);

        trainingDao.create(training);

        verify(trainingMap, never()).put(training.getTrainingName(), training);
    }

    @Test
    void testSelectExistingTraining() {
        String name = "ExistingTraining";
        Training training = generateTraining(1111L, 2222L);
        when(trainingMap.containsKey(name)).thenReturn(true);
        when(trainingMap.get(name)).thenReturn(training);

        Training result = trainingDao.select(name);

        verify(trainingMap, times(1)).get(name);
        assertEquals(training, result);
    }

    @Test
    void testSelectNonExistingTraining() {
        String name = "NonExistingTraining";
        when(trainingMap.containsKey(name)).thenReturn(false);

        Training result = trainingDao.select(name);

        verify(trainingMap, never()).get(name);
        assertNull(result);
    }

    @Test
    void testFindAll() {
        Training training1 = generateTraining(1111L, 2222L);
        Training training2 = generateTraining(3333L, 4444L);
        when(trainingMap.values()).thenReturn(List.of(training1, training2));

        List<Training> result = trainingDao.findAll();

        assertEquals(2, result.size(), "The result list should contain two training instances.");
        assertTrue(result.containsAll(List.of(training1, training2)), "The result list should contain the expected training instances.");
    }

    private Training generateTraining(Long id1, Long id2) {
        return new Training(
                id1,
                id2,
                "spar",
                new TrainingType("Box"),
                LocalDate.parse("1992-05-13"),
                2.0
        );
    }
}