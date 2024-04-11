package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dto.training.CreateTrainingDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.repository.TraineeRepository;
import com.davidmaisuradze.gymapplication.repository.TrainerRepository;
import com.davidmaisuradze.gymapplication.repository.TrainingRepository;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.davidmaisuradze.gymapplication.service.impl.TraineeServiceImpl.getTrainee;
import static com.davidmaisuradze.gymapplication.service.impl.TrainerServiceImpl.getTrainer;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;

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

        trainingRepository.save(training);
    }

    private Trainee findTraineeProfileByUsername(String username) {
        return getTrainee(username, traineeRepository);
    }

    private Trainer findTrainerProfileByUsername(String username) {
        return getTrainer(username, trainerRepository);
    }
}
