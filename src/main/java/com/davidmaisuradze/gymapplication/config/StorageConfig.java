package com.davidmaisuradze.gymapplication.config;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.davidmaisuradze.gymapplication")
@Slf4j
public class StorageConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean(name = "traineeStorage")
    public Map<Long, Trainee> traineeMap() {
        log.info("Trainee Storage Bean Crated");
        return new HashMap<>();
    }

    @Bean(name = "trainerStorage")
    public Map<Long, Trainer> trainerMap() {
        log.info("Trainer Storage Bean Crated");
        return new HashMap<>();
    }

    @Bean(name = "trainingStorage")
    public Map<String, Training> trainingMap() {
        log.info("Training Storage Bean Crated");
        return new HashMap<>();
    }

}
