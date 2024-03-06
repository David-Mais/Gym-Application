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
        Trainer trainer = generateTrainer(1L);
        trainerService.create(trainer);
        verify(trainerDao).create(trainer);
    }

    @Test
    void testUpdate() {
        Trainer trainer = generateTrainer(1L);
        trainerService.update(trainer);
        verify(trainerDao).update(trainer);
    }

    @Test
    void testSelect() {
        long id = 1L;
        Trainer trainer = generateTrainer(1L);
        when(trainerDao.findById(id)).thenReturn(trainer);

        Trainer result = trainerService.findById(id);

        verify(trainerDao).findById(id);
        assertEquals(trainer, result);
    }

    @Test
    void testSelectAll() {
        Trainer trainer1 = generateTrainer(1L);
        Trainer trainer2 = generateTrainer(2L);
        List<Trainer> expectedTrainers = Arrays.asList(trainer1, trainer2);
        when(trainerDao.findAll()).thenReturn(expectedTrainers);

        List<Trainer> result = trainerService.selectAll();

        verify(trainerDao).findAll();
        assertEquals(expectedTrainers, result);
    }

    private Trainer generateTrainer(Long id) {
        return Trainer
                .builder()
                .firstName("first")
                .lastName("last")
                .username("username")
                .password("password")
                .isActive(true)
                .specialization("spec")
                .userId(id)
                .build();
    }
}
