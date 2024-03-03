package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.config.StorageConfig;
import com.davidmaisuradze.gymapplication.model.Trainee;
import com.davidmaisuradze.gymapplication.model.Trainer;
import com.davidmaisuradze.gymapplication.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigTest {
    private StorageConfig storageConfig;

    @BeforeEach
    void setUp() {
        storageConfig = new StorageConfig();
    }

    @Test
    void traineeMapTest() {
        Map<Long, Trainee> traineeMap = storageConfig.traineeMap();
        assertNotNull(traineeMap, "Trainee map should not be null");
        assertTrue(traineeMap.isEmpty(), "Trainee map should be empty");
        assertTrue(traineeMap instanceof HashMap, "Trainee map should be an instance of HashMap");
    }

    @Test
    void trainerMapTest() {
        Map<Long, Trainer> trainerMap = storageConfig.trainerMap();
        assertNotNull(trainerMap, "Trainer map should not be null");
        assertTrue(trainerMap.isEmpty(), "Trainer map should be empty");
        assertTrue(trainerMap instanceof HashMap, "Trainer map should be an instance of HashMap");
    }

    @Test
    void trainingMapTest() {
        Map<String, Training> trainingMap = storageConfig.trainingMap();
        assertNotNull(trainingMap, "Training map should not be null");
        assertTrue(trainingMap.isEmpty(), "Training map should be empty");
        assertTrue(trainingMap instanceof HashMap, "Training map should be an instance of HashMap");
    }
}
