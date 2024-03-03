package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.model.Trainer;

import java.util.List;

public interface TrainerDao {
    void create(Trainer trainer);
    void update(Trainer trainer);
    Trainer select(long id);
    List<Trainer> findAll();
}
