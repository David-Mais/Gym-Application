package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dto.TraineeDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class TestController {
    @Autowired
    private TraineeDao traineeDao;
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!";
    }

    @GetMapping(value = "/trainees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TraineeDto>> traineeList() {
        List<Trainee> fetched = traineeDao.findAll();
        List<TraineeDto> dtos = new ArrayList<>();
        for (Trainee t : fetched) {
            dtos.add(TraineeDto
                    .builder()
                    .id(t.getId())
                    .firstName(t.getFirstName())
                    .lastName(t.getLastName())
                    .username(t.getUsername())
                    .password(t.getPassword())
                    .isActive(t.getIsActive())
                    .dateOfBirth(t.getDateOfBirth())
                    .address(t.getAddress())
                    .build()
            );
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(dtos);
    }
}
