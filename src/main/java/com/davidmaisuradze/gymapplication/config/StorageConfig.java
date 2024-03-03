package com.davidmaisuradze.gymapplication.config;

import com.davidmaisuradze.gymapplication.model.Trainee;
import com.davidmaisuradze.gymapplication.model.Trainer;
import com.davidmaisuradze.gymapplication.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StorageConfig {
    Logger logger = LoggerFactory.getLogger(StorageConfig.class);

    @Bean(name = "traineeMap")
    public Map<Long, Trainee> traineeMap() {
        logger.info("Trainee map bean created successfully");
        return new HashMap<>();
    }

    @Bean(name = "trainerMap")
    public Map<Long, Trainer> trainerMap() {
        logger.info("Trainer map bean created successfully");
        return new HashMap<>();
    }

    @Bean(name = "trainingMap")
    public Map<String, Training> trainingMap() {
        logger.info("Training map bean created successfully");
        return new HashMap<>();
    }
}
