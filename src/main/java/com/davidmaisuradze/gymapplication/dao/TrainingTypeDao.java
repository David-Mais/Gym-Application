package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.TrainingType;

import java.util.List;

public interface TrainingTypeDao {
    TrainingType findTrainingTypeByName(String name);
    List<TrainingType> findAll();
}
