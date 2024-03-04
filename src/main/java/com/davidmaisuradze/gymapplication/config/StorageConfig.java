package com.davidmaisuradze.gymapplication.config;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
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
public class StorageConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    @Bean(name = "traineeMap")
    public Map<Long, Trainee> traineeMap() {
        return new HashMap<>();
    }

    @Bean(name = "trainerMap")
    public Map<Long, Trainer> trainerMap() {
        return new HashMap<>();
    }

    @Bean(name = "trainingMap")
    public Map<String, Training> trainingMap() {
        return new HashMap<>();
    }

}
