package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
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
import com.davidmaisuradze.gymapplication.mapper.TrainingTypeMapper;
import com.davidmaisuradze.gymapplication.mapper.UserMapper;
import com.davidmaisuradze.gymapplication.service.TraineeService;
import com.davidmaisuradze.gymapplication.util.DetailsGenerator;
import jakarta.persistence.NoResultException;
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
    private final TrainerDao trainerDao;
    private final TrainingTypeDao trainingTypeDao;
    private final DetailsGenerator detailsGenerator;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final UserMapper userMapper;
    private final TrainingMapper trainingMapper;
    private final TrainingTypeMapper trainingTypeMapper;


    @Override
    @Transactional
    public CredentialsDto create(CreateTraineeDto createTraineeDto) {
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
    }

    @Override
    public TraineeProfileDto getProfile(String username) {
        Trainee trainee = findTraineeProfileByUsername(username);
        TraineeProfileDto profileDto = traineeMapper.traineeToTrainerProfileDto(trainee);
        profileDto.setTrainersList(getAllTrainerDto(username));

        return profileDto;
    }

    @Override
    @Transactional
    public TraineeProfileUpdateResponseDto updateProfile(
            String username,
            TraineeProfileUpdateRequestDto updateRequestDto
    ) {
        Trainee trainee = findTraineeProfileByUsername(username);

        updateProfileHelper(trainee, updateRequestDto);

        TraineeProfileUpdateResponseDto responseDto = traineeMapper.traineeToUpdateResponseDto(trainee);

        responseDto.setTrainersList(getAllTrainerDto(username));

        return responseDto;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        Trainee traineeToDelete = findTraineeProfileByUsername(username);
        traineeDao.delete(traineeToDelete);
    }

    @Override
    @Transactional
    public void updateActiveStatus(String username, ActiveStatusDto activeStatusDto) {
        Trainee trainee = findTraineeProfileByUsername(username);
        trainee.setIsActive(activeStatusDto.getIsActive());
        traineeDao.update(trainee);
    }

    @Override
    @Transactional
    public List<TrainingInfoDto> getTrainingsList(String username, TrainingSearchCriteria criteria) {
        trainingSearchValidator(username, criteria);

        List<Training> trainings = traineeDao.getTrainingsList(username, criteria);
        List<TrainingInfoDto> trainingInfoDtos = new ArrayList<>();

        for (Training t : trainings) {
            TrainingInfoDto trainingInfo = trainingMapper.trainingToTrainingInfoDto(t);

            trainingInfo.setUsername(t.getTrainer().getUsername());

            if (criteria.getTrainingTypeName() != null) {
                trainingInfo.setTrainingType(
                        trainingTypeMapper
                                .entityToDto(trainingTypeDao.findTrainingTypeByName(criteria.getTrainingTypeName()))
                );
            }
            trainingInfoDtos.add(trainingInfo);
        }

        if (trainingInfoDtos.isEmpty()) {
            throw new GymException("No Trainings found", "404");
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

    private void updateProfileHelper(Trainee trainee, TraineeProfileUpdateRequestDto updateRequestDto) {
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
    }

    private Trainee findTraineeProfileByUsername(String username) {
        try {
            return traineeDao.findByUsername(username);
        } catch (NoResultException e) {
            throw new GymException("Trainee not found with username: " + username, "404");
        }
    }

    private boolean trainerExists(String username) {
        try {
            return trainerDao.findByUsername(username) != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    private boolean trainingTypeExists(String typeName) {
        try {
            return trainingTypeDao.findTrainingTypeByName(typeName) != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    private void trainingSearchValidator(String username, TrainingSearchCriteria criteria) {
        findTraineeProfileByUsername(username);

        if (criteria.getName() != null && (!trainerExists(criteria.getName()))) {
            throw new GymException("Trainer not found with username: " + criteria.getName(), "404");
        }
        if (criteria.getTrainingTypeName() != null && (!trainingTypeExists(criteria.getTrainingTypeName()))) {
            throw new GymException("Training type not found with name: " + criteria.getTrainingTypeName(), "404");
        }
    }
}
