package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainer;

import java.util.List;

public interface TrainerDao {
    Trainer create(Trainer trainer);
    Trainer update(Trainer trainer);
    Trainer findById(Long id);
    List<Trainer> findAll();
}
