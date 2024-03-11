package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.dao.config.DataSourceConfig;
import com.davidmaisuradze.gymapplication.dao.config.HibernateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Slf4j
public class GymApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(DataSourceConfig.class, HibernateConfig.class);
        context.refresh();
    }

}
