package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.config.StorageConfig;
import com.davidmaisuradze.gymapplication.initializer.EntityInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GymApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(StorageConfig.class);
        EntityInitializer initializer = (EntityInitializer) applicationContext.getBean("entityInitializer");
        initializer.getTraineeMap().values().forEach(System.out::println);
        initializer.getTrainerMap().values().forEach(System.out::println);
        initializer.getTrainingMap().values().forEach(System.out::println);
    }

}
