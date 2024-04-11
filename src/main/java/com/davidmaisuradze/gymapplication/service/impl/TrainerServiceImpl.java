package com.davidmaisuradze.gymapplication.service.impl;

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
import com.davidmaisuradze.gymapplication.repository.TraineeRepository;
import com.davidmaisuradze.gymapplication.repository.TrainerRepository;
import com.davidmaisuradze.gymapplication.repository.TrainingTypeRepository;
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
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;
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
        trainerRepository.save(trainer);

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
        trainerRepository.save(trainer);
        log.info("Trainer={} isActive={}", username, isActive);
    }

    @Override
    public List<TrainerInfoDto> getTrainersNotAssigned(String username) {
        if (traineeNotExists(username)) {
            throw new GymException("Trainee not found with username: " + username, "404");
        }
        return trainerRepository
                .getTrainersNotAssigned(username)
                .stream()
                .map(trainerMapper::trainerToTrainerInfoDto)
                .toList();
    }

    @Override
    public List<TrainingInfoDto> getTrainingsList(String username, TrainerTrainingSearchDto criteria) {
        trainingSearchValidator(username, criteria);

        List<Training> trainings = trainerRepository.getTrainingsList(
                username,
                criteria.getFrom(),
                criteria.getTo(),
                criteria.getName()
        );
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
        return trainerRepository.getAllTrainees(username)
                .stream()
                .map(traineeMapper::traineeToTraineeInfoDto)
                .toList();
    }

    private TrainingType findTrainingTypeByName(String typeName) {
        try {
            TrainingType type = trainingTypeRepository.findByTrainingTypeName(typeName);
            if (type == null) {
                throw new GymException("Training type not found", "404");
            }
            return type;
        } catch (NoResultException e) {
            throw new GymException("Error finding training type with name: : " + typeName, "500");
        }
    }

    private Trainer findTrainerProfileByUsername(String username) {
        return getTrainer(username, trainerRepository);
    }

    static Trainer getTrainer(String username, TrainerRepository trainerRepository) {
        try {
            Trainer trainer = trainerRepository.findByUsername(username);
            if (trainer == null) {
                throw new GymException("Trainer not found with username: " + username, "404");
            }
            return trainer;
        } catch (Exception e) {
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

        trainerRepository.save(trainer);
    }

    private boolean traineeNotExists(String username) {
        try {
            return traineeRepository.findByUsername(username) == null;
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
