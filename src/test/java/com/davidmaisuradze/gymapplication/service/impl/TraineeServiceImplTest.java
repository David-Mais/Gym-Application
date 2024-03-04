package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
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
class TraineeServiceImplTest {
    @Mock
    private TraineeDao traineeDao;
    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    void testCreate() {
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        traineeService.create(trainee);
        verify(traineeDao).create(trainee);
    }

    @Test
    void testUpdate() {
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        traineeService.update(trainee);
        verify(traineeDao).update(trainee);
    }

    @Test
    void testDelete() {
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        traineeService.delete(trainee);
        verify(traineeDao).delete(trainee);
    }

    @Test
    void testSelect() {
        long id = 1L;
        Trainee trainee = new Trainee(
                "first",
                "last",
                "username",
                "pass",
                true,
                LocalDate.parse("2004-09-20"),
                "addr",
                1234);
        when(traineeDao.select(id)).thenReturn(trainee);

        Trainee result = traineeService.select(id);

        verify(traineeDao).select(id);
        assertEquals(trainee, result);
    }

    @Test
    void testSelectAll() {
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
        List<Trainee> expectedTrainees = Arrays.asList(trainee1, trainee2);
        when(traineeDao.findAll()).thenReturn(expectedTrainees);

        List<Trainee> result = traineeService.selectAll();

        verify(traineeDao).findAll();
        assertEquals(expectedTrainees, result);
    }
}