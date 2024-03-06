package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Training;

import java.util.List;

public interface TrainingService {
    Training crete(Training training);
    Training findByName(String name);
    List<Training> findAll();
}
