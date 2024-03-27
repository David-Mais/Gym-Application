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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainee")
@RequiredArgsConstructor
public class TraineeController {
    private final TraineeService traineeService;
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

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

    @PutMapping("/login/change")
    public ResponseEntity<String> changeLogin(@RequestBody PasswordChangeDto passwordChangeDto) {
        boolean changed = traineeService.changePassword(passwordChangeDto);

        if (changed) {
            return ResponseEntity.ok("Password changed");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problem encountered during password change");
    }

    @GetMapping("/profile")
    public ResponseEntity<TraineeProfileDto> getProfile(@RequestBody CredentialsDto credentialsDto) {
        TraineeProfileDto profile = traineeService.getProfile(credentialsDto);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        }
        return null;
    }

    @PutMapping("/profile/update")
    public ResponseEntity<TraineeProfileUpdateResponseDto> updateProfile(
            @RequestBody TraineeProfileUpdateRequestDto traineeProfileUpdateRequestDto
    ) {
        TraineeProfileUpdateResponseDto responseDto = traineeService.updateProfile(traineeProfileUpdateRequestDto);

        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody CredentialsDto credentialsDto) {
        traineeService.deleteByUsername(credentialsDto);
        return ResponseEntity.status(HttpStatus.OK).body("Trainee Deleted Successfully");
    }

    @GetMapping("/trainings")
    //issue
    public List<TrainingInfoDto> getTrainings(
            @RequestBody TrainingSearchCriteria criteria,
            @RequestBody CredentialsDto credentialsDto
    ) {
        return traineeService.getTrainingsList(credentialsDto, criteria);
    }
}
