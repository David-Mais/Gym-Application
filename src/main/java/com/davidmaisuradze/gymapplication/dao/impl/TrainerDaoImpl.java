package com.davidmaisuradze.gymapplication.dao.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class TrainerDaoImpl implements TrainerDao {

    @Override
    public Trainer create(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer findByUsername(String username) {
        return null;
    }

    @Override
    public Trainer update(Trainer trainer) {
        return null;
    }
}
