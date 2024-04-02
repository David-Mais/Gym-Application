package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.mapper.TraineeMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainerMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainingMapper;
import com.davidmaisuradze.gymapplication.mapper.UserMapper;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import com.davidmaisuradze.gymapplication.service.util.DetailsGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerServiceImpl implements TrainerService {
    private final TrainerDao trainerDao;
    private final TraineeDao traineeDao;
    private final UserDao userDao;
    private final TrainingTypeDao trainingTypeDao;
    private final DetailsGenerator detailsGenerator;
    private final TrainerMapper trainerMapper;
    private final TraineeMapper traineeMapper;
    private final UserMapper userMapper;
    private final TrainingMapper trainingMapper;
    private static final String CREDENTIAL_MATCH = "Credentials checked username and password matched";
    private static final String CREDENTIAL_MISMATCH = "Username and password not matched";

    @Override
    @Transactional
    public CredentialsDto create(CreateTrainerDto createTrainerDto) {
        try {
            String password = detailsGenerator.generatePassword();
            String username = detailsGenerator.generateUsername(
                    createTrainerDto.getFirstName(),
                    createTrainerDto.getLastName()
            );

            Trainer trainer = trainerMapper.createTrainerDtoToTrainer(createTrainerDto);
            TrainingType specialization = trainingTypeDao.findTrainingTypeByName(createTrainerDto.getSpecialization().getTrainingTypeName());

            trainer.setPassword(password);
            trainer.setUsername(username);
            trainer.setIsActive(true);
            trainer.setSpecialization(specialization);
            trainerDao.create(trainer);

            log.info("Trainer Created");

            return userMapper.userToCredentialsDto(trainer);
        } catch (Exception e) {
            throw new GymException("First name Last name and specialization should be provided", "400");
        }
    }

    @Override
    public TrainerProfileDto getProfile(String username) {
        try {
            Trainer trainer = trainerDao.findByUsername(username);
            TrainerProfileDto trainerProfile = trainerMapper.trainerToTrainerProfileDto(trainer);

            trainerProfile.setTraineesList(getAllTraineeInfoDto(username));

            return trainerProfile;
        } catch (Exception e) {
            throw new GymException("Trainer not found with username: " + username, "404");
        }
    }

    @Override
    @Transactional
    public TrainerProfileUpdateResponseDto updateProfile(
            String username,
            TrainerProfileUpdateRequestDto requestDto
    ) {
        Trainer trainer = trainerDao.findByUsername(username);

        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        TrainingType specialization = requestDto.getSpecialization();
        Boolean isActive = requestDto.getIsActive();

        if (firstName != null) {
            trainer.setFirstName(firstName);
        }
        if (lastName != null) {
            trainer.setLastName(lastName);
        }
        if (specialization != null) {
            trainer.setSpecialization(trainingTypeDao.findTrainingTypeByName(specialization.getTrainingTypeName()));
        }
        if (isActive != null) {
            trainer.setIsActive(isActive);
        }

        trainerDao.update(trainer);

        TrainerProfileUpdateResponseDto responseDto = trainerMapper.trainerToUpdateResponseDto(trainer);

        responseDto.setTraineesList(getAllTraineeInfoDto(username));

        return responseDto;
    }

    @Override
    public Trainer findByUsername(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainer trainer = trainerDao.findByUsername(username);
            if (trainer == null) {
                log.warn("Trainer with username: {} not found", username);
                return null;
            }
            return trainer;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public Trainer update(Trainer trainer) {
        if (userDao.checkCredentials(trainer.getUsername(), trainer.getPassword())) {
            log.info(CREDENTIAL_MATCH);
            return trainerDao.update(trainer);
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public void updateActiveStatus(String username, ActiveStatusDto activeStatusDto) {
        boolean isActive = activeStatusDto.getIsActive();
        Trainer trainer = trainerDao.findByUsername(username);
        trainer.setIsActive(isActive);
        trainerDao.update(trainer);
        log.info("Trainer={} isActive={}", username, isActive);
    }

    @Override
    public List<TrainerInfoDto> getTrainersNotAssigned(String username) {
        try {
            traineeDao.findByUsername(username);
        } catch (Exception e) {
            throw new GymException("No Trainee with username: " + username, "404");
        }
        return trainerDao
                .getTrainersNotAssigned(username)
                .stream()
                .map(trainerMapper::trainerToTrainerInfoDto)
                .toList();
    }

    @Override
    public List<TrainingInfoDto> getTrainingsList(String username, TrainerTrainingSearchDto criteria) {
        List<Training> trainings = trainerDao.getTrainingsList(criteria);

        List<TrainingInfoDto> trainingInfoDtos = new ArrayList<>();

        for (Training t : trainings) {
            if (t.getTrainer().getUsername().equals(username)) {
                TrainingInfoDto trainingInfo = trainingMapper.trainingToTrainingInfoDto(t);
                trainingInfo.setUsername(t.getTrainee().getUsername());
                trainingInfo.setTrainingType(t.getTrainer().getSpecialization());
                trainingInfoDtos.add(trainingInfo);
            }
        }

        return trainingInfoDtos;
    }

    private List<TraineeInfoDto> getAllTraineeInfoDto(String username) {
        return trainerDao.getAllTrainees(username)
                .stream()
                .map(traineeMapper::traineeToTraineeInfoDto)
                .toList();
    }
}
