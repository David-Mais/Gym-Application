package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.dto.training.CreateTrainingDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.mapper.TrainingMapper;
import com.davidmaisuradze.gymapplication.service.TrainingService;
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
    private final TrainingMapper trainingMapper;

    @Override
    @Transactional
    public TrainingDto create(CreateTrainingDto createTrainingDto) {
        Trainee trainee = traineeDao.findByUsername(createTrainingDto.getTraineeUsername());
        Trainer trainer = trainerDao.findByUsername(createTrainingDto.getTrainerUsername());
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

        log.info("Training Created");

        return trainingMapper.entityToDto(training);
    }
}
