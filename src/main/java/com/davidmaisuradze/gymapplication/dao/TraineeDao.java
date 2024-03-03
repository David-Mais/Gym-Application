package com.davidmaisuradze.gymapplication.dao;

import com.davidmaisuradze.gymapplication.model.Trainee;

import java.util.List;

public interface TraineeDao {
    void create(Trainee trainee);
    void update(Trainee trainee);
    void delete(Trainee trainee);
    Trainee select(long id);
    List<Trainee> findAll();
}
