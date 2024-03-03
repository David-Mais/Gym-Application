package com.davidmaisuradze.gymapplication.initializer;

import com.davidmaisuradze.gymapplication.model.Trainee;
import com.davidmaisuradze.gymapplication.model.Trainer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TrainerInitializer {
    Logger logger = LoggerFactory.getLogger(TrainerInitializer.class);
    private Map<Long, Trainee> traineeMap;
    private Map<Long, Trainer> trainerMap;

    @Value("${data.file.path.trainer}")
    private String dataPath;

    @Autowired
    public void setTraineeMap(@Qualifier("traineeMap") Map<Long, Trainee> traineeMap) {
        this.traineeMap = traineeMap;
    }

    @Autowired
    public void setTrainerMap(@Qualifier("trainerMap") Map<Long, Trainer> trainerMap) {
        this.trainerMap = trainerMap;
    }

    @PostConstruct
    public void initialize() {
        ClassPathResource resource = new ClassPathResource(dataPath);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String message = String.format("starting to read from: %s", dataPath);
            logger.info(message);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(" ");
                String firstName = attributes[0];
                String lastName = attributes[1];
                String userName = usernameGenerator(firstName, lastName);
                String password = RandomPassGenerator.generatePassword();
                boolean isActive = true;
                String specialization = attributes[2];
                long userId = Long.parseLong(attributes[3]);
                Trainer trainer = new Trainer(
                        firstName,
                        lastName,
                        userName,
                        password,
                        isActive,
                        specialization,
                        userId
                );
                trainerMap.put(userId, trainer);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public Map<Long, Trainer> getTrainerMap() {
        return trainerMap;
    }

    private String usernameGenerator(String first, String last) {
        int counter = 0;
        StringBuilder builder = new StringBuilder();
        builder.append(first).append(".").append(last);
        List<String> usernames = new ArrayList<>();
        for (Trainee t : traineeMap.values()) {
            usernames.add(t.getUserName());
        }
        for (Trainer t : trainerMap.values()) {
            usernames.add(t.getUserName());
        }
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
}
