package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.trainingtype.TrainingTypeDto;
import com.davidmaisuradze.gymapplication.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainingtypes")
@RequiredArgsConstructor
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<TrainingTypeDto>> getAll() {
        return ResponseEntity.ok(trainingTypeService.findAll());
    }
}
