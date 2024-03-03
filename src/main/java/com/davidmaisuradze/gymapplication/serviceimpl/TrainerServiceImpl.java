package com.davidmaisuradze.gymapplication.serviceimpl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.model.Trainer;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {
    private TrainerDao trainerDao;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Override
    public void create(Trainer trainer) {
        trainerDao.create(trainer);
    }

    @Override
    public void update(Trainer trainer) {
        trainerDao.update(trainer);
    }

    @Override
    public Trainer select(long id) {
        return trainerDao.select(id);
    }

    @Override
    public List<Trainer> selectAll() {
        return trainerDao.findAll();
    }
}
