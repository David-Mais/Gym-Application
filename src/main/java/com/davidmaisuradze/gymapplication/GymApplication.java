package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.initializer.TraineeInitializer;
import com.davidmaisuradze.gymapplication.initializer.TrainerInitializer;
import com.davidmaisuradze.gymapplication.initializer.TrainingInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GymApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GymApplication.class, args);
        TraineeInitializer trainee = context.getBean(TraineeInitializer.class);
        TrainerInitializer trainer = context.getBean(TrainerInitializer.class);
        trainee.getTraineeMap().values().forEach(System.out::println);
        trainer.getTrainerMap().values().forEach(System.out::println);
        TrainingInitializer training = context.getBean(TrainingInitializer.class);
        System.out.println(training.getTrainingMap());
    }

}
