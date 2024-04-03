package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
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
import com.davidmaisuradze.gymapplication.mapper.TrainingTypeMapper;
import com.davidmaisuradze.gymapplication.mapper.UserMapper;
import com.davidmaisuradze.gymapplication.service.TrainerService;
import com.davidmaisuradze.gymapplication.util.DetailsGenerator;
import jakarta.persistence.NoResultException;
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
    private final TrainingTypeDao trainingTypeDao;
    private final DetailsGenerator detailsGenerator;
    private final TrainerMapper trainerMapper;
    private final TraineeMapper traineeMapper;
    private final UserMapper userMapper;
    private final TrainingMapper trainingMapper;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    @Transactional
    public CredentialsDto create(CreateTrainerDto createTrainerDto) {
        String password = detailsGenerator.generatePassword();
        String username = detailsGenerator.generateUsername(
                createTrainerDto.getFirstName(),
                createTrainerDto.getLastName()
        );

        Trainer trainer = trainerMapper.createTrainerDtoToTrainer(createTrainerDto);
        TrainingType specialization = findTrainingTypeByName(createTrainerDto.getSpecialization().getTrainingTypeName());

        trainer.setPassword(password);
        trainer.setUsername(username);
        trainer.setIsActive(true);
        trainer.setSpecialization(specialization);
        trainerDao.create(trainer);

        log.info("Trainer Created");

        return userMapper.userToCredentialsDto(trainer);
    }

    @Override
    public TrainerProfileDto getProfile(String username) {
        Trainer trainer = findTrainerProfileByUsername(username);
        TrainerProfileDto trainerProfile = trainerMapper.trainerToTrainerProfileDto(trainer);

        trainerProfile.setTraineesList(getAllTraineeInfoDto(username));

        return trainerProfile;
    }

    @Override
    @Transactional
    public TrainerProfileUpdateResponseDto updateProfile(
            String username,
            TrainerProfileUpdateRequestDto requestDto
    ) {
        Trainer trainer = findTrainerProfileByUsername(username);

        updateProfileHelper(trainer, requestDto);

        TrainerProfileUpdateResponseDto responseDto = trainerMapper.trainerToUpdateResponseDto(trainer);

        responseDto.setTraineesList(getAllTraineeInfoDto(username));

        return responseDto;
    }

    @Override
    @Transactional
    public void updateActiveStatus(String username, ActiveStatusDto activeStatusDto) {
        boolean isActive = activeStatusDto.getIsActive();
        Trainer trainer = findTrainerProfileByUsername(username);
        trainer.setIsActive(isActive);
        trainerDao.update(trainer);
        log.info("Trainer={} isActive={}", username, isActive);
    }

    @Override
    public List<TrainerInfoDto> getTrainersNotAssigned(String username) {
        if (traineeNotExists(username)) {
            throw new GymException("Trainee not found with username: " + username, "404");
        }
        return trainerDao
                .getTrainersNotAssigned(username)
                .stream()
                .map(trainerMapper::trainerToTrainerInfoDto)
                .toList();
    }

    @Override
    public List<TrainingInfoDto> getTrainingsList(String username, TrainerTrainingSearchDto criteria) {
        trainingSearchValidator(username, criteria);

        List<Training> trainings = trainerDao.getTrainingsList(username, criteria);
        List<TrainingInfoDto> trainingInfoDtos = new ArrayList<>();

        for (Training t : trainings) {
            TrainingInfoDto trainingInfo = trainingMapper.trainingToTrainingInfoDto(t);
            trainingInfo.setUsername(t.getTrainee().getUsername());
            trainingInfo.setTrainingType(trainingTypeMapper.entityToDto(t.getTrainer().getSpecialization()));
            trainingInfoDtos.add(trainingInfo);
        }

        if (trainingInfoDtos.isEmpty()) {
            throw new GymException("No Trainings found", "404");
        }

        return trainingInfoDtos;
    }

    private List<TraineeInfoDto> getAllTraineeInfoDto(String username) {
        return trainerDao.getAllTrainees(username)
                .stream()
                .map(traineeMapper::traineeToTraineeInfoDto)
                .toList();
    }

    private TrainingType findTrainingTypeByName(String typeName) {
        try {
            return trainingTypeDao.findTrainingTypeByName(typeName);
        } catch (NoResultException e) {
            throw new GymException("Training type not found with name: " + typeName, "404");
        }
    }

    private Trainer findTrainerProfileByUsername(String username) {
        try {
            return trainerDao.findByUsername(username);
        } catch (NoResultException e) {
            throw new GymException("Trainer not found with username: " + username, "404");
        }
    }

    private void updateProfileHelper(Trainer trainer, TrainerProfileUpdateRequestDto requestDto) {
        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        Boolean isActive = requestDto.getIsActive();

        if (firstName != null) {
            trainer.setFirstName(firstName);
        }
        if (lastName != null) {
            trainer.setLastName(lastName);
        }
        if (isActive != null) {
            trainer.setIsActive(isActive);
        }

        trainerDao.update(trainer);
    }

    private boolean traineeNotExists(String username) {
        try {
            return traineeDao.findByUsername(username) == null;
        } catch (NoResultException e) {
            return true;
        }
    }

    private void trainingSearchValidator(String username, TrainerTrainingSearchDto criteria) {
        findTrainerProfileByUsername(username);

        if (criteria.getName() != null && (traineeNotExists(criteria.getName()))) {
            throw new GymException("Trainee not found with username: " + criteria.getName(), "404");
        }
    }
}
