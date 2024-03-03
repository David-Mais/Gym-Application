package com.davidmaisuradze.gymapplication.serviceimpl;

import com.davidmaisuradze.gymapplication.dao.TrainingDao;
import com.davidmaisuradze.gymapplication.model.Training;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {
    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    public void crete(Training training) {
        trainingDao.create(training);
    }

    @Override
    public Training select(String name) {
        return trainingDao.select(name);
    }

    @Override
    public List<Training> findAll() {
        return trainingDao.findAll();
    }
}
