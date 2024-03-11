package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.config.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Slf4j
public class GymApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(DatabaseConfig.class);
    }

}
