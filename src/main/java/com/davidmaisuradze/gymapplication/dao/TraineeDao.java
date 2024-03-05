package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.entity.Trainee;

import java.util.List;

public interface TraineeDao {
    Trainee create(Trainee trainee);
    Trainee update(Trainee trainee);
    void delete(Trainee trainee);
    Trainee select(Long id);
    List<Trainee> findAll();
}
