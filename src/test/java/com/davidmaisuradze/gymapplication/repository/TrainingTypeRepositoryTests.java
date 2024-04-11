package com.davidmaisuradze.gymapplication.repository;

import com.davidmaisuradze.gymapplication.entity.TrainingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql(scripts = "/database/test-schema.sql")
@ActiveProfiles("test")
class TrainingTypeRepositoryTests {
    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    @Test
    void findTrainingTypeByName_WhenTrainingTypeExists_ReturnsTrainingType() {
        String name = "mma";
        TrainingType trainingType = trainingTypeRepository.findByTrainingTypeName(name);
        assertThat(trainingType).isNotNull();
        assertThat(trainingType.getTrainingTypeName()).isEqualTo(name);
    }

    @Test
    void findTrainingTypeByName_WhenTrainingTypeDoesNotExist_ReturnsNull() {
        String name = "yoga";
        TrainingType trainingType = trainingTypeRepository.findByTrainingTypeName(name);
        assertThat(trainingType).isNull();
    }
}