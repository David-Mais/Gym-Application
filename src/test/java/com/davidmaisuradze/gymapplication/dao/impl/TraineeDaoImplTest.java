package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeDaoImplTest {
    @Mock
    private Map<Long, Trainee> traineeMap;

    @InjectMocks
    private TraineeDaoImpl traineeDao;
    @Test
    void testCreateNewTrainee() {
        Trainee trainee = generateTrainee(1L);
        long id = trainee.getUserId();
        when(traineeMap.containsKey(id)).thenReturn(false);
        traineeDao.create(trainee);
        id = trainee.getUserId();
        verify(traineeMap, times(1)).put(id, trainee);
    }

    @Test
    void testUpdateExistingTrainee() {
        Trainee trainee = generateTrainee(1L);
        long id = trainee.getUserId();
        when(traineeMap.containsKey(id)).thenReturn(true);
        traineeDao.update(trainee);
        verify(traineeMap, times(1)).put(id, trainee);
    }

    @Test
    void testDeleteExistingTrainee() {
        Trainee trainee = generateTrainee(1L);
        trainee.setUserId(1234L);
        long id = trainee.getUserId();

        when(traineeMap.containsKey(id)).thenReturn(true);

        traineeDao.delete(trainee);

        verify(traineeMap, times(1)).remove(id);
    }

    @Test
    void testDeleteNonExistingTrainee() {
        Trainee trainee = generateTrainee(1L);
        trainee.setUserId(1234L);
        long id = trainee.getUserId();

        when(traineeMap.containsKey(id)).thenReturn(false);

        traineeDao.delete(trainee);

        verify(traineeMap, never()).remove(id);
    }

    @Test
    void testSelectExistingTrainee() {
        long id = 1;
        Trainee trainee = generateTrainee(1L);
        when(traineeMap.containsKey(id)).thenReturn(true);
        when(traineeMap.get(id)).thenReturn(trainee);

        Trainee result = traineeDao.select(id);

        verify(traineeMap, times(1)).get(id);
        assertEquals(trainee, result);
    }

    @Test
    void testSelectNonExistingTrainee() {
        long id = 1L;
        when(traineeMap.containsKey(id)).thenReturn(false);

        Trainee result = traineeDao.select(id);

        verify(traineeMap, never()).get(id);
        assertNull(result);
    }

    @Test
    void testFindAll() {
        Trainee trainee1 = generateTrainee(1L);
        Trainee trainee2 = generateTrainee(2L);
        when(traineeMap.values()).thenReturn(Arrays.asList(trainee1, trainee2));

        List<Trainee> result = traineeDao.findAll();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(trainee1, trainee2)));
    }

    private Trainee generateTrainee(Long id) {
        return Trainee
                .builder()
                .firstName("first")
                .lastName("last")
                .username("username")
                .password("pass")
                .isActive(true)
                .dateOfBirth(LocalDate.parse("2004-09-20"))
                .address("addr")
                .userId(id)
                .build();
    }
}