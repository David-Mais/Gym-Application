package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class Facade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;


    //Trainee methods
    public void createTrainee(Trainee trainee) {
        traineeService.create(trainee);
    }

    public void updateTrainee(Trainee trainee) {
        traineeService.update(trainee);
    }

    public void deleteTrainee(Trainee trainee) {
        traineeService.update(trainee);
    }

    public Trainee selectTrainee(long id) {
        return traineeService.select(id);
    }

    public List<Trainee> selectAllTrainees() {
        return traineeService.selectAll();
    }

    //Trainer methods
    public void createTrainer(Trainer trainer) {
        trainerService.create(trainer);
    }

    public void updateTrainer(Trainer trainer) {
        trainerService.update(trainer);
    }

    public Trainer selectTrainer(long id) {
        return trainerService.select(id);
    }

    public List<Trainer> selectAllTrainers() {
        return trainerService.selectAll();
    }

    //Training methods
    public void createTraining(Training training) {
        trainingService.crete(training);
    }

    public Training selectTraining(String name) {
        return trainingService.select(name);
    }

    public List<Training> selectAllTrainings() {
        return trainingService.findAll();
    }
}
