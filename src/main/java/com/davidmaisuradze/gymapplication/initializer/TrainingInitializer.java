package com.davidmaisuradze.gymapplication.initializer;

import com.davidmaisuradze.gymapplication.model.Training;
import com.davidmaisuradze.gymapplication.model.TrainingType;
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
import java.time.LocalDate;
import java.util.Map;

@Component
public class TrainingInitializer {
    Logger logger = LoggerFactory.getLogger(TrainingInitializer.class);
    private Map<String, Training> trainingMap;

    @Value("${data.file.path.training}")
    private String dataPath;

    @Autowired
    public void setTrainingMap(@Qualifier("trainingMap") Map<String, Training> trainingMap) {
        this.trainingMap = trainingMap;
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
                long traineeId = Long.parseLong(attributes[0]);
                long trainerId = Long.parseLong(attributes[1]);
                String trainingName = attributes[2];
                TrainingType trainingType = new TrainingType(attributes[3]);
                LocalDate trainingDate = LocalDate.parse(attributes[4]);
                double duration = Double.parseDouble(attributes[5]);

                Training training = new Training(
                        traineeId,
                        trainerId,
                        trainingName,
                        trainingType,
                        trainingDate,
                        duration
                );

                trainingMap.put(trainingName, training);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public Map<String, Training> getTrainingMap() {
        logger.info("Training map returned");
        return trainingMap;
    }
}
