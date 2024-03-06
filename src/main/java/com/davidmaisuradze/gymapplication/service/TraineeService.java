package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.entity.Trainee;

import java.util.List;

public interface TraineeService {
    Trainee create(Trainee trainee);
    Trainee update(Trainee trainee);
    void delete(Trainee trainee);
    Trainee findById(Long id);
    List<Trainee> selectAll();
}
