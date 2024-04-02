package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.training.CreateTrainingDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingDto;
import com.davidmaisuradze.gymapplication.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {
    private final TrainingService trainingService;

    @PostMapping()
    public ResponseEntity<TrainingDto> create(
            @Valid @RequestBody CreateTrainingDto createTrainingDto
    ) {
        TrainingDto dto = trainingService.create(createTrainingDto);
        return ResponseEntity.ok(dto);
    }
}
