package com.davidmaisuradze.gymapplication;

import com.davidmaisuradze.gymapplication.daoimpl.TraineeDaoImpl;
import com.davidmaisuradze.gymapplication.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TraineeDaoImplTest {
    private TraineeDaoImpl traineeDao;


    @Autowired
    public void setTraineeDao(TraineeDaoImpl traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Test
    public void testSelect() {
        Trainee trainee = traineeDao.select(11111);
        assertEquals(trainee.getFirstName(), "David");
        assertEquals(trainee.getLastName(), "Maisuradze");
    }

    @Test
    public void testFindAll() {
        List<Trainee> trainees = traineeDao.findAll();
        assertNotNull(trainees, "List should not be null");
        assertFalse(trainees.isEmpty(), "List should not be empty");
    }
}
