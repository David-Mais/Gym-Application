package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trainees")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService traineeService;

    @PostMapping()
    public ResponseEntity<CredentialsDto> createTrainee(
            @Valid @RequestBody CreateTraineeDto createTraineeDto
    ) {
        CredentialsDto credentialsDto = traineeService.create(createTraineeDto);
        return new ResponseEntity<>(credentialsDto, HttpStatus.CREATED);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<TraineeProfileDto> getProfile(
            @PathVariable String username
    ) {
        return ResponseEntity.ok(traineeService.getProfile(username));
    }

    @PutMapping("/profile/{username}")
    public ResponseEntity<TraineeProfileUpdateResponseDto> updateProfile(
            @PathVariable String username,
            @Valid @RequestBody TraineeProfileUpdateRequestDto traineeProfileUpdateRequestDto
    ) {
        return ResponseEntity.ok(traineeService.updateProfile(username, traineeProfileUpdateRequestDto));
    }

    @DeleteMapping("/profile/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        traineeService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{username}/active")
    public ResponseEntity<Void> activate(
            @PathVariable String username,
            @Valid @RequestBody ActiveStatusDto activeStatusDto
    ) {
        traineeService.updateActiveStatus(username, activeStatusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/{username}/trainings")
    public List<TrainingInfoDto> getTrainings(
            @PathVariable String username,
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "trainerUsername", required = false) String trainerName,
            @RequestParam(name = "trainingTypeName", required = false) String trainingTypeName
    ) {
        TrainingSearchCriteria criteria = TrainingSearchCriteria.builder()
                .from(from)
                .to(to)
                .name(trainerName)
                .trainingTypeName(trainingTypeName)
                .build();
        return traineeService.getTrainingsList(username, criteria);
    }
}
