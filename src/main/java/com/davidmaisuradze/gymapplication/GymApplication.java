package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.config.DataSourceConfig;
import com.davidmaisuradze.gymapplication.config.HibernateConfig;
import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;


@Slf4j
public class GymApplication {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
//                DataSourceConfig.class, HibernateConfig.class);

//        DataSource dataSource = context.getBean(DataSource.class);
//
//        if (dataSource instanceof HikariDataSource) {
//            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
//
//            String jdbcUrl = hikariDataSource.getJdbcUrl();
//            System.out.println("DataSource JDBC URL: " + jdbcUrl);
//        } else {
//            System.out.println("DataSource is not an instance of HikariDataSource. Cannot retrieve JDBC URL directly.");
//        }
//
//        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
//        System.out.println("EntityManagerFactory bean retrieved successfully: " + emf.toString());


//        TraineeDao dao = context.getBean(TraineeDao.class);
//        UserDao userDao = context.getBean(UserDao.class);
//        TrainerDao trainerDao = context.getBean(TrainerDao.class);
//        User user = userDao.findByUsername("Davit.Maisuradze");
//        System.out.println(user.toString());

//        Trainee trainee = dao.findByUsername("Davit.Maisuradze");
//        System.out.println(trainee);
//        System.out.println(dao.findByUsername("Mariam.Katamashvili"));

//        Trainer trainer = trainerDao.findByUsername("Mariam.Katamashvili1");
//        System.out.println(trainer);
    }

}
