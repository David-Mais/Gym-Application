package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TraineeDaoImplTest {
    @Mock
    private Map<Long, Trainee> traineeMap;

    @InjectMocks
    private TraineeDaoImpl traineeDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateNewTrainee() {
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        long id = trainee.getUserId();
        when(traineeMap.containsKey(id)).thenReturn(false);
        traineeDao.create(trainee);
        verify(traineeMap, times(1)).put(id, trainee);
    }

    @Test
    void update() {
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        long id = trainee.getUserId();
        when(traineeMap.containsKey(id)).thenReturn(true);
        traineeDao.create(trainee);
        verify(traineeMap, never()).put(eq(id), any(Trainee.class));
    }

    @Test
    void testDeleteExistingTrainee() {
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        trainee.setUserId(1234L);
        long id = trainee.getUserId();

        when(traineeMap.containsKey(id)).thenReturn(true);

        traineeDao.delete(trainee);

        verify(traineeMap, times(1)).remove(id);
    }

    @Test
    void testDeleteNonExistingTrainee() {
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        trainee.setUserId(1234L);
        long id = trainee.getUserId();

        when(traineeMap.containsKey(id)).thenReturn(false);

        traineeDao.delete(trainee);

        verify(traineeMap, never()).remove(id);
    }

    @Test
    void testSelectExistingTrainee() {
        long id = 1;
        Trainee expectedTrainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1L);
        when(traineeMap.containsKey(id)).thenReturn(true);
        when(traineeMap.get(id)).thenReturn(expectedTrainee);

        Trainee result = traineeDao.select(id);

        verify(traineeMap, times(1)).get(id);
        assertEquals(expectedTrainee, result);
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
        Trainee trainee1 = new Trainee(
                "first 1",
                "last 1",
                "username 1",
                "pass 1",
                true,
                LocalDate.parse("2004-09-20"),
                "addr 1",
                1L);
        Trainee trainee2 = new Trainee(
                "first 2",
                "last 2",
                "username 2",
                "pass 2",
                false,
                LocalDate.parse("2009-01-23"),
                "addr 2",
                2L);
        when(traineeMap.values()).thenReturn(Arrays.asList(trainee1, trainee2));

        List<Trainee> result = traineeDao.findAll();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(trainee1, trainee2)));
    }
}