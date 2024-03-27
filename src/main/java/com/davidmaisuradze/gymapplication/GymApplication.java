package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.config.ApplicationConfig;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import com.davidmaisuradze.gymapplication.service.TrainingTypeService;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;


@Slf4j
public class GymApplication {
    private static final String DAVID_KHELADZE = "David.Kheladze";

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);


        //DataSource and EntityManagerFactory demonstration
        DataSource dataSource = context.getBean(DataSource.class);

        if (dataSource instanceof HikariDataSource hikariDataSource) {

            String jdbcUrl = hikariDataSource.getJdbcUrl();
            log.info("DataSource JDBC URL: {}", jdbcUrl);
        } else {
            log.warn("DataSource is not an instance of HikariDataSource. Cannot retrieve JDBC URL directly.");
        }

        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        log.info("EntityManagerFactory bean retrieved successfully: {}", emf);


        //Creating Services
        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TrainingService trainingService = context.getBean(TrainingService.class);
        TrainingTypeService trainingTypeService = context.getBean(TrainingTypeService.class);


        //Testing TraineeService methods
        //create
//        Trainee trainee = Trainee.builder()
//                .firstName("Davit")
//                .lastName("Maisuradze")
//                .isActive(true)
//                .dateOfBirth(LocalDate.parse("2004-09-20"))
//                .address("Kutaisi")
//                .build();
//        traineeService.create(traineeM);
//
//
//        //Find by username
//        Trainee traineeByUsername = traineeService.findByUsername("Davit.Maisuradze", "newPass");
//        log.info(traineeByUsername.toString());
//
//        //update
//        trainee.setDateOfBirth(LocalDate.parse("2000-01-01"));
//        traineeService.update(trainee);
//
//        //delete by username
//        traineeService.deleteByUsername("Mariam.Katamashvili", "marimagaria");
//
//        //changePassword
//        traineeService.changePassword("Davit.Maisuradze", "newPass", "changedPass");
//
//        //deactivate
//        traineeService.deactivate(DAVID_KHELADZE, "davdav");
//
//        //activate
//        traineeService.activate(DAVID_KHELADZE, "davdav");
//
//        //get trainings
//        TrainingSearchCriteria traineeCriteria = TrainingSearchCriteria
//                .builder()
//                .from(LocalDate.parse("1990-05-19"))
//                .to(LocalDate.parse("2010-11-25"))
//                .build();
//        List<Training> trainingsByCriteria = traineeService.getTrainingsList(traineeCriteria);
//        log.info(trainingsByCriteria.toString());
//
//        TrainingType trainingType = trainingTypeService.findTrainingTypeByName("box");
//        TrainingSearchCriteria criteriaWithType = TrainingSearchCriteria
//                .builder()
//                .from(LocalDate.parse("1990-05-19"))
//                .to(LocalDate.parse("2010-11-25"))
//                .trainingType(trainingType)
//                .build();
//        List<Training> trainingsByCriteriaWithType = traineeService.getTrainingsList(criteriaWithType);
//        log.info(trainingsByCriteriaWithType.toString());
//
//
//
//        //Trainer Service methods
//        //create
//        TrainingType box = trainingTypeService.findTrainingTypeByName("box");
//        Trainer trainer = Trainer.builder()
//                .firstName("Beka")
//                .lastName("Dumbadze")
//                .isActive(true)
//                .specialization(box)
//                .build();
//        trainerService.create(trainer);
//
//        //find by username
//        Trainer iliaTopuria = trainerService.findByUsername("Ilia.Topuria", "ufcchamp");
//        log.info(iliaTopuria.toString());
//
//        //update
//        TrainingType iliaBox = trainingTypeService.findTrainingTypeByName("box");
//        iliaTopuria.setSpecialization(iliaBox);
//        trainerService.update(iliaTopuria);
//
//        //change password
//        trainerService.changePassword("Salome.Chachua", "salosalo", "New Password");
//
//        //activate deactivate
//        trainerService.deactivate("Merab.Dvalishvili", "merabmerab");
//
//        trainerService.activate("Merab.Dvalishvili", "merabmerab");
//
//        //trainers not assigned
//        List<Trainer> trainersNotAssigned = trainerService.getTrainersNotAssigned(DAVID_KHELADZE);
//        log.info(trainersNotAssigned.toString());
//
//
//
//        //training service
//        //create
//        Training training = Training
//                .builder()
//                .trainee(trainee)
//                .trainer(iliaTopuria)
//                .trainingName("Created Training")
//                .trainingType(box)
//                .trainingDate(LocalDate.parse("2024-03-15"))
//                .duration(100)
//                .build();
//        trainingService.create(training);
    }

}
