package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/trainers")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    @PostMapping()
    public ResponseEntity<CredentialsDto> createTrainer(
            @Valid @RequestBody CreateTrainerDto createTrainerDto
    ) {
        CredentialsDto credentialsDto = trainerService.create(createTrainerDto);
        return new ResponseEntity<>(credentialsDto, HttpStatus.CREATED);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<TrainerProfileDto> getProfile(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(trainerService.getProfile(username));
    }

    @PutMapping("/profile/{username}")
    public ResponseEntity<TrainerProfileUpdateResponseDto> updateProfile(
            @PathVariable("username") String username,
            @Valid @RequestBody TrainerProfileUpdateRequestDto trainerProfileUpdateRequestDto
    ) {
        return ResponseEntity.ok(trainerService.updateProfile(username, trainerProfileUpdateRequestDto));
    }

    @GetMapping("/not-assigned/{username}")
    public List<TrainerInfoDto> getTrainersNotAssigned(
            @PathVariable("username") String username
    ) {
        return trainerService.getTrainersNotAssigned(username);
    }

    @PatchMapping("/{username}/active")
    public ResponseEntity<Void> activate(
            @PathVariable("username") String username,
            @Valid @RequestBody ActiveStatusDto activeStatusDto
    ) {
        trainerService.updateActiveStatus(username, activeStatusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/{username}/trainings")
    public List<TrainingInfoDto> getTrainings(
            @PathVariable("username") String username,
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "traineeUsername", required = false) String traineeName
    ) {
        TrainerTrainingSearchDto criteria = TrainerTrainingSearchDto.builder()
                .from(from)
                .to(to)
                .name(traineeName)
                .build();
        return trainerService.getTrainingsList(username, criteria);
    }
}
