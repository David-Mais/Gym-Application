package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.model.Trainee;

import java.util.List;

public interface TraineeService {
    void create(Trainee trainee);
    void update(Trainee trainee);
    void delete(Trainee trainee);
    Trainee select(long id);
    List<Trainee> selectAll();
}
