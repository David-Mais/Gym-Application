package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {
    @Mock
    private TrainerDao trainerDao;
    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void testCreate() {
        Trainer trainer = new Trainer(
                "first",
                "last",
                "username",
                "pass",
                true,
                "spec",
                1234L);
        trainerService.create(trainer);
        verify(trainerDao).create(trainer);
    }

    @Test
    void testUpdate() {
        Trainer trainer = new Trainer(
                "first",
                "last",
                "username",
                "pass",
                true,
                "spec",
                1234L);
        trainerService.update(trainer);
        verify(trainerDao).update(trainer);
    }

    @Test
    void testSelect() {
        long id = 1L;
        Trainer trainer = new Trainer(
                "first",
                "last",
                "username",
                "pass",
                true,
                "spec",
                1234L);
        when(trainerDao.select(id)).thenReturn(trainer);

        Trainer result = trainerService.select(id);

        verify(trainerDao).select(id);
        assertEquals(trainer, result);
    }

    @Test
    void testSelectAll() {
        Trainer trainer1 = new Trainer(
                "first",
                "last",
                "username",
                "pass",
                true,
                "spec",
                1111L);
        Trainer trainer2 = new Trainer(
                "first2",
                "last2",
                "username2",
                "pass2",
                false,
                "spec2",
                2222L);
        List<Trainer> expectedTrainers = Arrays.asList(trainer1, trainer2);
        when(trainerDao.findAll()).thenReturn(expectedTrainers);

        List<Trainer> result = trainerService.selectAll();

        verify(trainerDao).findAll();
        assertEquals(expectedTrainers, result);
    }
}
