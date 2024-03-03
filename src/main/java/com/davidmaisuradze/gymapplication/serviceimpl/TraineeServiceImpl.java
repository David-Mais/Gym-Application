package com.davidmaisuradze.gymapplication.serviceimpl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.model.Trainee;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    private TraineeDao traineeDao;

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
        logger.info("traineeDao injected successfully");
    }

    @Override
    public void create(Trainee trainee) {
        traineeDao.create(trainee);
    }

    @Override
    public void update(Trainee trainee) {
        traineeDao.update(trainee);
    }

    @Override
    public void delete(Trainee trainee) {
        traineeDao.delete(trainee);
    }

    @Override
    public Trainee select(long id) {
        return traineeDao.select(id);
    }

    @Override
    public List<Trainee> selectAll() {
        return traineeDao.findAll();
    }
}
