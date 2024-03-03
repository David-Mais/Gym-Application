package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Trainer;

import java.util.List;

public interface TrainerService {
    void create(Trainer trainer);
    void update(Trainer trainer);
    Trainer select(long id);
    List<Trainer> selectAll();
}
