package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.config.StorageConfig;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.initializer.EntityInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Slf4j
public class GymApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(StorageConfig.class);
        EntityInitializer initializer = (EntityInitializer) applicationContext.getBean("entityInitializer");
        for (Trainee trainee : initializer.getTraineeMap().values()) {
            log.info(trainee.toString());
        }
        for (Trainer trainer : initializer.getTrainerMap().values()) {
            log.info(trainer.toString());
        }
        for (Training training : initializer.getTrainingMap().values()) {
            log.info(training.toString());
        }
    }

}
