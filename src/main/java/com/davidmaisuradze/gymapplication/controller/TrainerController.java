package com.davidmaisuradze.gymapplication.controller;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainer")
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

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

    @PutMapping("/login/change")
    public ResponseEntity<String> changeLogin(@RequestBody PasswordChangeDto passwordChangeDto) {
        boolean changed = trainerService.changePassword(passwordChangeDto);

        if (changed) {
            return ResponseEntity.ok("Password changed");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Problem encountered during password change");
    }

    @GetMapping("/profile")
    public ResponseEntity<TrainerProfileDto> getProfile(
            @RequestBody CredentialsDto credentialsDto
    ) {
        TrainerProfileDto profileDto = trainerService.getProfile(credentialsDto);
        if (profileDto != null) {
            return ResponseEntity.ok(profileDto);
        }
        return null;
    }

    @PutMapping("/profile/update")
    public ResponseEntity<TrainerProfileUpdateResponseDto> updateProfile(
            @RequestBody TrainerProfileUpdateRequestDto trainerProfileUpdateRequestDto
    ) {
        TrainerProfileUpdateResponseDto responseDto = trainerService.updateProfile(trainerProfileUpdateRequestDto);

        if (responseDto != null) {
            return ResponseEntity.ok(responseDto);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/not-assigned")
    public List<TrainerInfoDto> getTrainersNotAssigned(
            @RequestBody CredentialsDto credentialsDto
    ) {
        return trainerService.getTrainersNotAssigned(credentialsDto);
    }

    @PatchMapping("/activate")
    public ResponseEntity<String> activate(@RequestBody CredentialsDto credentialsDto) {
        trainerService.activate(credentialsDto);
        return ResponseEntity.ok("Trainee activated");
    }

    @PatchMapping("/deactivate")
    public ResponseEntity<String> deactivate(@RequestBody CredentialsDto credentialsDto) {
        trainerService.deactivate(credentialsDto);
        return ResponseEntity.ok("Trainee deactivated");
    }
}
