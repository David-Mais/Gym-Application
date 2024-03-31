package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.service.TrainerService;
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
@RequestMapping("/api/v1/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    @PostMapping("/create")
    public ResponseEntity<CredentialsDto> createTrainer(@RequestBody CreateTrainerDto createTrainerDto) {
        CredentialsDto credentialsDto = trainerService.create(createTrainerDto);
        return new ResponseEntity<>(credentialsDto, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialsDto credentialsDto) {
        TrainerDto trainerDto = trainerService.login(credentialsDto);

        if (trainerDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Login Successful");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credentials not matched");
    }

    @PutMapping("/login/change/{username}")
    public ResponseEntity<String> changeLogin(
            @PathVariable String username,
            @RequestBody PasswordChangeDto passwordChangeDto
    ) {
        boolean changed = trainerService.changePassword(username, passwordChangeDto);

        if (changed) {
            return ResponseEntity.ok("Password changed");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problem encountered during password change");
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<TrainerProfileDto> getProfile(
            @PathVariable String username
    ) {
        TrainerProfileDto profileDto = trainerService.getProfile(username);
        if (profileDto != null) {
            return ResponseEntity.ok(profileDto);
        }
        return null;
    }

    @PutMapping("/profile/update/{username}")
    public ResponseEntity<TrainerProfileUpdateResponseDto> updateProfile(
            @PathVariable String username,
            @RequestBody TrainerProfileUpdateRequestDto trainerProfileUpdateRequestDto
    ) {
        TrainerProfileUpdateResponseDto responseDto = trainerService.updateProfile(username, trainerProfileUpdateRequestDto);

        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/not-assigned/{username}")
    public List<TrainerInfoDto> getTrainersNotAssigned(
            @PathVariable String username
    ) {
        return trainerService.getTrainersNotAssigned(username);
    }

    @PatchMapping("/profile/activate/{username}")
    public ResponseEntity<String> activate(@PathVariable String username) {
        trainerService.activate(username);
        return ResponseEntity.ok("Trainer activated");
    }

    @PatchMapping("/profile/deactivate/{username}")
    public ResponseEntity<String> deactivate(@PathVariable String username) {
        trainerService.deactivate(username);
        return ResponseEntity.ok("Trainer deactivated");
    }

    @GetMapping("/profile/{username}/trainings")
    public List<TrainingInfoDto> getTrainings(
            @PathVariable String username,
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "traineeUsername", required = false) String traineeName
    ) {
        TrainerTrainingSearchDto criteria =  TrainerTrainingSearchDto.builder()
                .from(from)
                .to(to)
                .name(traineeName)
                .build();
        return trainerService.getTrainingsList(username, criteria);
    }
}
