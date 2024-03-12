package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.config.DataSourceConfig;
import com.davidmaisuradze.gymapplication.config.HibernateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Slf4j
public class GymApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DataSourceConfig.class, HibernateConfig.class, ApplicationConfig.class);

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



//        Trainee trainee = Trainee
//                .builder()
//                .firstName("Davit")
//                .lastName("Maisuradze")
//                .isActive(true)
//                .address("KIU Dorm")
//                .dateOfBirth(Date.valueOf("2004-09-20"))
//                .build();
//
//        TraineeService service = context.getBean(TraineeService.class);
//        Trainee createdTrainee = service.create(trainee);
//        System.out.println(createdTrainee);




//        Trainer trainer = trainerDao.findByUsername("Mariam.Katamashvili1");
//        System.out.println(trainer);


//        Authenticator authenticator = context.getBean(Authenticator.class);
//        System.out.println(authenticator.checkCredentials("Davit.Maissuradze", "newPass"));
    }

}
