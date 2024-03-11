package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Repository
@Slf4j
public class TraineeDaoImpl  implements TraineeDao {

    @Override
    public Trainee create(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee findByUsername(String username) {
        return null;
    }

    @Override
    public Trainee update(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee deleteByUsername(String username) {
        return null;
    }
}
