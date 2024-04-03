package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.dto.training.CreateTrainingDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    private final TrainingDao trainingDao;
    private final TrainerDao trainerDao;
    private final TraineeDao traineeDao;

    @Override
    @Transactional
    public void create(CreateTrainingDto createTrainingDto) {
        Trainee trainee = findTraineeProfileByUsername(createTrainingDto.getTraineeUsername());
        Trainer trainer = findTrainerProfileByUsername(createTrainingDto.getTrainerUsername());
        String name = createTrainingDto.getTrainingName();
        TrainingType trainingType = trainer.getSpecialization();
        LocalDate trainingDate = createTrainingDto.getTrainingDate();
        Integer duration = createTrainingDto.getDuration();

        Training training = Training
                .builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingName(name)
                .trainingType(trainingType)
                .trainingDate(trainingDate)
                .duration(duration)
                .build();

        trainingDao.create(training);
    }

    private Trainee findTraineeProfileByUsername(String username) {
        try {
            return traineeDao.findByUsername(username);
        } catch (NoResultException e) {
            throw new GymException("Trainee not found with username: " + username, "404");
        }
    }

    private Trainer findTrainerProfileByUsername(String username) {
        try {
            return trainerDao.findByUsername(username);
        } catch (NoResultException e) {
            throw new GymException("Trainer not found with username: " + username, "404");
        }
    }
}
