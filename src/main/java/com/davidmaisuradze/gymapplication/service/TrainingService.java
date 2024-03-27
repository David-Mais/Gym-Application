package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.training.CreateTrainingDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingDto;

public interface TrainingService {
    TrainingDto create(CreateTrainingDto createTrainingDto);
}
