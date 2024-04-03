package com.davidmaisuradze.gymapplication.util;

import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Getter
public class TrainingQueryBuilder {
    private final StringBuilder jpql = new StringBuilder("SELECT t FROM Training t WHERE 1=1");
    private final Map<String, Object> parameters = new HashMap<>();

    public void withTrainerUsername(String trainerUsername) {
        if (trainerUsername != null) {
            jpql.append(" AND t.trainer.username = :trainerUsername");
            parameters.put("trainerUsername", trainerUsername);
        }
    }

    public void withTraineeUsername(String traineeUsername) {
        if (traineeUsername != null) {
            jpql.append(" AND t.trainee.username = :username");
            parameters.put("username", traineeUsername);
        }
    }

    public void withFromDate(LocalDate fromDate) {
        if (fromDate != null) {
            jpql.append(" AND t.trainingDate > :from");
            parameters.put("from", fromDate);
        }
    }

    public void withToDate(LocalDate toDate) {
        if (toDate != null) {
            jpql.append(" AND t.trainingDate < :to");
            parameters.put("to", toDate);
        }
    }

    public void withTraineeName(String name) {
        if (name != null) {
            jpql.append(" AND t.trainee.username = :traineeName");
            parameters.put("traineeName", name);
        }
    }

    public void withTrainerName(String name) {
        if (name != null) {
            jpql.append(" AND t.trainer.username = :trainerName");
            parameters.put("trainerName", name);
        }
    }

    public void withTrainingTypeName(String trainingTypeName) {
        if (trainingTypeName != null) {
            jpql.append(" AND t.trainingType.trainingTypeName = :trainingTypeName");
            parameters.put("trainingTypeName", trainingTypeName);
        }
    }
}
