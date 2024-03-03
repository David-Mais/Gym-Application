package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Training;

import java.util.List;

public interface TrainingService {
    void crete(Training training);
    Training select(String name);
    List<Training> findAll();
}
