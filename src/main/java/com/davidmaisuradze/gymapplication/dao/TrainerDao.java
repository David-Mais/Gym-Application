package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainer;

public interface TrainerDao {
    Trainer create(Trainer trainer);
    Trainer findByUsername(String username);
    Trainer update(Trainer trainer);
}
