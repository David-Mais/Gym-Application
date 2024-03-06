package com.davidmaisuradze.gymapplication.initializer;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.entity.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@Slf4j
public class EntityInitializer {
    @Getter
    private Map<Long, Trainee> traineeMap;
    @Getter
    private Map<Long, Trainer> trainerMap;
    @Getter
    private Map<String, Training> trainingMap;

    @Value("${data.file.path.trainee}")
    private String traineeDataPath;
    @Value("${data.file.path.trainer}")
    private String trainerDataPath;
    @Value("${data.file.path.training}")
    private String trainingDataPath;

    private TraineeDao traineeDao;
    private TrainerDao trainerDao;

    @Autowired
    public void setTrainingMap(
            @Qualifier("traineeStorage")  Map<Long, Trainee> traineeMap,
            @Qualifier("trainerStorage")  Map<Long, Trainer> trainerMap,
            @Qualifier("trainingStorage") Map<String, Training> trainingMap
    ) {
        this.traineeMap = traineeMap;
        this.trainerMap = trainerMap;
        this.trainingMap = trainingMap;
        log.info("Entity maps injected");
    }

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @PostConstruct
    public void initialize() {
        initializeTrainees();
        initializeTrainers();
        initializeTrainings();
        log.info("All data initialized");
    }

    public void initializeTrainees() {
        log.info("Initializing trainees from file: {}", traineeDataPath);
        ClassPathResource resource = new ClassPathResource(traineeDataPath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug("Processing trainee line: {}", line);
                User user = userInfo(line);
                LocalDate dateOfBirth = LocalDate.parse(line.split(" ")[2]);
                String address = line.split(" ")[3];
                Trainee trainee = Trainee
                        .builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .isActive(user.isActive())
                        .dateOfBirth(dateOfBirth)
                        .address(address)
                        .build();

                traineeDao.create(trainee);
                log.info("Trainee added: {} {}", user.getFirstName(), user.getLastName());
            }
        } catch (IOException e) {
            log.error("Error reading trainee data from file: {}", traineeDataPath, e);
        }
    }

    public void initializeTrainings() {
        log.info("Initializing trainings from file: {}", trainingDataPath);
        ClassPathResource resource = new ClassPathResource(trainingDataPath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug("Processing training line: {}", line);
                String[] attributes = line.split(" ");
                Long traineeId = Long.parseLong(attributes[0]);
                Long trainerId = Long.parseLong(attributes[1]);
                String trainingName = attributes[2];
                TrainingType trainingType = TrainingType
                        .builder()
                        .trainingTypeName(attributes[3])
                        .build();
                LocalDate trainingDate = LocalDate.parse(attributes[4]);
                double duration = Double.parseDouble(attributes[5]);

                Training training = Training
                        .builder()
                        .traineeId(traineeId)
                        .trainerId(trainerId)
                        .trainingName(trainingName)
                        .trainingType(trainingType)
                        .trainingDate(trainingDate)
                        .duration(duration)
                        .build();
                trainingMap.put(trainingName, training);
                log.info("Training added: {}", trainingName);
            }
        } catch (IOException e) {
            log.error("Error reading training data from file: {}", trainingDataPath, e);
        }
    }

    public void initializeTrainers() {
        log.info("Initializing trainers from file: {}", trainerDataPath);
        ClassPathResource resource = new ClassPathResource(trainerDataPath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = userInfo(line);
                String specialization = line.split(" ")[2];
                Trainer trainer = Trainer
                        .builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .isActive(user.isActive())
                        .specialization(specialization)
                        .build();
                trainerDao.create(trainer);
                log.info("Trainer added: {} {}", user.getFirstName(), user.getLastName());
            }
        } catch (IOException e) {
            log.error("Error reading trainer data from file: {}", trainerDataPath, e);
        }
    }
    private String usernameGenerator(String first, String last) {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(first).append(".").append(last);
        List<String> usernames = Stream
                .concat(traineeMap.values().stream(), trainerMap.values().stream())
                .map(User::getUsername)
                .toList();
        while (true) {
            int counterBefore = counter;
            for (String username : usernames) {
                if (username.contentEquals(builder)) {
                    counter++;
                }
            }
            if (counter != 0) {
                builder.setLength(0);
                builder.append(first);
                builder.append(".");
                builder.append(last);
                builder.append(counter);
            }
            if (counterBefore == counter) {
                break;
            }
        }
        return builder.toString();
    }

    private String generatePassword() {
        return UUID.randomUUID()
                .toString()
                .substring(0,8);
    }

    private User userInfo(String line) {
        String[] attributes = line.split(" ");
        String firstName = attributes[0];
        String lastName = attributes[1];
        String userName = usernameGenerator(firstName, lastName);
        String password = generatePassword();
        boolean isActive = true;
        return User
                .builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(userName)
                .password(password)
                .isActive(isActive)
                .build();
    }
}
