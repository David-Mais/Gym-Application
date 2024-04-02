package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.mapper.TraineeMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainerMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainingMapper;
import com.davidmaisuradze.gymapplication.mapper.UserMapper;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import com.davidmaisuradze.gymapplication.service.util.DetailsGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TraineeServiceImpl implements TraineeService {
    private final TraineeDao traineeDao;
    private final UserDao userDao;
    private final TrainingTypeDao trainingTypeDao;
    private final DetailsGenerator detailsGenerator;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final UserMapper userMapper;
    private final TrainingMapper trainingMapper;
    private static final String CREDENTIAL_MATCH = "Credentials checked username and password matched";
    private static final String CREDENTIAL_MISMATCH = "Username and password not matched";


    @Override
    @Transactional
    public CredentialsDto create(CreateTraineeDto createTraineeDto) {
        try {
            String password = detailsGenerator.generatePassword();
            String username = detailsGenerator.generateUsername(
                    createTraineeDto.getFirstName(),
                    createTraineeDto.getLastName()
            );

            Trainee trainee = traineeMapper.createTraineeDtoToTrainee(createTraineeDto);

            trainee.setPassword(password);
            trainee.setUsername(username);
            trainee.setIsActive(true);

            traineeDao.create(trainee);

            log.info("Trainee Created");

            return userMapper.userToCredentialsDto(trainee);
        } catch (Exception e) {
            throw new GymException("First name Last name and date of birth should be provided", "400");
        }
    }

    @Override
    public TraineeProfileDto getProfile(String username) {
        try {
            Trainee trainee = traineeDao.findByUsername(username);
            TraineeProfileDto profileDto = traineeMapper.traineeToTrainerProfileDto(trainee);
            profileDto.setTrainersList(getAllTrainerDto(username));

            return profileDto;
        } catch (Exception e) {
            throw new GymException("Trainee not found with username: " + username, "404");
        }
    }

    @Override
    @Transactional
    public TraineeProfileUpdateResponseDto updateProfile(
            String username,
            TraineeProfileUpdateRequestDto updateRequestDto
    ) {
        Trainee trainee = traineeDao.findByUsername(username);

        String firstName = updateRequestDto.getFirstName();
        String lastName = updateRequestDto.getLastName();
        LocalDate dob = updateRequestDto.getDateOfBirth();
        String address = updateRequestDto.getAddress();
        Boolean isActive = updateRequestDto.getIsActive();

        if (firstName != null) {
            trainee.setFirstName(firstName);
        }
        if (lastName != null) {
            trainee.setLastName(lastName);
        }
        if (dob != null) {
            trainee.setDateOfBirth(dob);
        }
        if (address != null) {
            trainee.setAddress(address);
        }
        if (isActive != null) {
            trainee.setIsActive(isActive);
        }

        traineeDao.update(trainee);

        TraineeProfileUpdateResponseDto responseDto = traineeMapper.traineeToUpdateResponseDto(trainee);

        responseDto.setTrainersList(getAllTrainerDto(username));

        return responseDto;
    }

    @Override
    public Trainee findByUsername(String username, String password) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainee trainee = traineeDao.findByUsername(username);
            if (trainee == null) {
                log.warn("Trainee with username: {} not found", username);
                return null;
            }
            return trainee;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        if (userDao.checkCredentials(trainee.getUsername(), trainee.getPassword())) {
            log.info(CREDENTIAL_MATCH);
            return traineeDao.update(trainee);
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        try {
            Trainee traineeToDelete = traineeDao.findByUsername(username);
            traineeDao.delete(traineeToDelete);
        } catch (Exception e) {
            throw new GymException("Trainee does not exist with username: " + username, "404");
        }
    }

    @Override
    @Transactional
    public void updateActiveStatus(String username, ActiveStatusDto activeStatusDto) {
        Trainee trainee = traineeDao.findByUsername(username);
        trainee.setIsActive(activeStatusDto.getIsActive());
        traineeDao.update(trainee);
    }

    @Override
    @Transactional
    public List<TrainingInfoDto> getTrainingsList(String username, TrainingSearchCriteria criteria) {
        List<Training> trainings = traineeDao.getTrainingsList(criteria);

        List<TrainingInfoDto> trainingInfoDtos = new ArrayList<>();

        for (Training t : trainings) {
            if (t.getTrainee().getUsername().equals(username)) {
                TrainingInfoDto trainingInfo = trainingMapper.trainingToTrainingInfoDto(t);
                trainingInfo.setUsername(t.getTrainer().getUsername());
                if (criteria.getTrainingTypeName() != null) {
                    trainingInfo.setTrainingType(trainingTypeDao.findTrainingTypeByName(criteria.getTrainingTypeName()));
                }
                trainingInfoDtos.add(trainingInfo);
            }
        }

        return trainingInfoDtos;
    }

    private List<TrainerInfoDto> getAllTrainerDto(String username) {
        return traineeDao
                .getAllTrainers(username)
                .stream()
                .map(trainerMapper::trainerToTrainerInfoDto)
                .toList();
    }
}
