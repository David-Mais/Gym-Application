package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainee;

public interface TraineeDao {
    Trainee create(Trainee trainee);
    Trainee findByUsername(String username);
    Trainee update(Trainee trainee);
    Trainee deleteByUsername(String username);
}
