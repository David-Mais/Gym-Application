package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.entity.Trainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerDaoImplTest {
    @Mock
    private Map<Long, Trainer> trainerMap;

    @InjectMocks
    private TrainerDaoImpl trainerDao;

    @Test
    void testCreateNewTrainer() {
        Trainer trainer = new Trainer(
                "first",
                "last",
                "username",
                "pass",
                true,
                "spec",
                1234);
        long id = trainer.getUserId();
        when(trainerMap.containsKey(id)).thenReturn(false);
        trainerDao.create(trainer);
        verify(trainerMap, times(1)).put(id, trainer);
    }

    @Test
    void testUpdateExistingTrainer() {
        Trainer trainer = new Trainer(
                "first",
                "last",
                "username",
                "pass",
                true,
                "spec",
                1234);
        long id = trainer.getUserId();
        when(trainerMap.containsKey(id)).thenReturn(true);
        trainerDao.update(trainer);
        verify(trainerMap, times(1)).put(id, trainer);
    }

    @Test
    void testSelectExistingTrainer() {
        long id = 1;
        Trainer trainer = new Trainer(
                "first",
                "last",
                "username",
                "pass",
                true,
                "spec",
                1234L);
        when(trainerMap.containsKey(id)).thenReturn(true);
        when(trainerMap.get(id)).thenReturn(trainer);

        Trainer result = trainerDao.select(id);

        verify(trainerMap, times(1)).get(id);
        assertEquals(trainer, result);
    }

    @Test
    void testSelectNonExistingTrainer() {
        long id = 1L;
        when(trainerMap.containsKey(id)).thenReturn(false);

        Trainer result = trainerDao.select(id);

        verify(trainerMap, never()).get(id);
        assertNull(result);
    }

    @Test
    void testFindAll() {
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
        when(trainerMap.values()).thenReturn(Arrays.asList(trainer1, trainer2));

        List<Trainer> result = trainerDao.findAll();

        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(trainer1, trainer2)));
    }
}