package com.davidmaisuradze.gymapplication.facade;

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
    public Trainee createTrainee(Trainee trainee) {
        return traineeService.create(trainee);
    }

    public Trainee updateTrainee(Trainee trainee) {
        return traineeService.update(trainee);
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
    public Trainer createTrainer(Trainer trainer) {
        return trainerService.create(trainer);
    }

    public Trainer updateTrainer(Trainer trainer) {
        return trainerService.update(trainer);
    }

    public Trainer selectTrainer(long id) {
        return trainerService.select(id);
    }

    public List<Trainer> selectAllTrainers() {
        return trainerService.selectAll();
    }

    //Training methods
    public Training createTraining(Training training) {
        return trainingService.crete(training);
    }

    public Training selectTraining(String name) {
        return trainingService.select(name);
    }

    public List<Training> selectAllTrainings() {
        return trainingService.findAll();
    }
}
