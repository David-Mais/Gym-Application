package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Training;

import java.util.List;

public interface TrainingDao {
    Training create(Training training);
    Training select(String name);
    List<Training> findAll();
}
