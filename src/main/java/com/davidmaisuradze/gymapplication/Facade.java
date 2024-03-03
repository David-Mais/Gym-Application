package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.service.TraineeService;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Facade {
    private final Logger logger = LoggerFactory.getLogger(Facade.class);
    private TraineeService traineeService;
    private TrainerService trainerService;
    private TrainingService trainingService;

    @Autowired
    public Facade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        logger.info("TraineeService, TrainerService and TrainingService injected successfully");
    }
}
