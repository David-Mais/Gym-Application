package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Trainer;

import java.util.List;

public interface TrainerService {
    Trainer create(Trainer trainer);
    Trainer update(Trainer trainer);
    Trainer findById(Long id);
    List<Trainer> selectAll();
}
