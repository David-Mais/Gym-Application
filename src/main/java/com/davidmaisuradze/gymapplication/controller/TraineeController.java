package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.service.TraineeService;
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
@RequestMapping("/api/v1/trainee")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService traineeService;

    @PostMapping("/create")
    public ResponseEntity<CredentialsDto> createTrainee(@RequestBody CreateTraineeDto createTraineeDto) {
        CredentialsDto credentialsDto = traineeService.create(createTraineeDto);
        return new ResponseEntity<>(credentialsDto, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody CredentialsDto credentialsDto) {
        TraineeDto traineeDto = traineeService.login(credentialsDto);

        if (traineeDto != null) {
            return ResponseEntity.ok("Login Successful");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credentials not matched");
    }

    @PutMapping("/login/change/{username}")
    public ResponseEntity<String> changeLogin(
            @PathVariable String username,
            @RequestBody PasswordChangeDto passwordChangeDto
    ) {
        boolean changed = traineeService.changePassword(username, passwordChangeDto);

        if (changed) {
            return ResponseEntity.ok("Password changed");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problem encountered during password change");
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<TraineeProfileDto> getProfile(
            @PathVariable String username
    ) {
        TraineeProfileDto profile = traineeService.getProfile(username);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        }
        return null;
    }

    @PutMapping("/profile/update/{username}")
    public ResponseEntity<TraineeProfileUpdateResponseDto> updateProfile(
            @PathVariable String username,
            @RequestBody TraineeProfileUpdateRequestDto traineeProfileUpdateRequestDto
    ) {
        TraineeProfileUpdateResponseDto responseDto =
                traineeService.updateProfile(username, traineeProfileUpdateRequestDto);

        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/profile/delete/{username}")
    public ResponseEntity<String> delete(@PathVariable String username) {
        traineeService.deleteByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body("Trainee Deleted Successfully");
    }

    @PatchMapping("/profile/activate/{username}")
    public ResponseEntity<String> activate(@PathVariable String username) {
        traineeService.activate(username);
        return ResponseEntity.ok("Trainee activated");
    }

    @PatchMapping("/profile/deactivate/{username}")
    public ResponseEntity<String> deactivate(@PathVariable String username) {
        traineeService.deactivate(username);
        return ResponseEntity.ok("Trainee deactivated");
    }

    @GetMapping("/profile/{username}/trainings")
    public List<TrainingInfoDto> getTrainings(
            @PathVariable String username,
            @RequestParam(name = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(name = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(name = "trainerUsername", required = false) String trainerName,
            @RequestParam(name = "trainingTypeName", required = false) String trainingTypeName
            ) {
        TrainingSearchCriteria criteria =  TrainingSearchCriteria.builder()
                .from(from)
                .to(to)
                .name(trainerName)
                .trainingTypeName(trainingTypeName)
                .build();
        return traineeService.getTrainingsList(username, criteria);
    }
}
