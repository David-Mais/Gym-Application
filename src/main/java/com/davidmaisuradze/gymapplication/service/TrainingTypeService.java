package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.TrainingType;

public interface TrainingTypeService {
    TrainingType findTrainingTypeByName(String name);
}
