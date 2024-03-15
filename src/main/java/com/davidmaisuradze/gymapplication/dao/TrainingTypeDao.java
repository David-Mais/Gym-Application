package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.TrainingType;

public interface TrainingTypeDao {
    TrainingType findTrainingTypeByName(String name);
}
