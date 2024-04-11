package com.davidmaisuradze.gymapplication.service.impl;

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
import com.davidmaisuradze.gymapplication.repository.TraineeRepository;
import com.davidmaisuradze.gymapplication.repository.TrainerRepository;
import com.davidmaisuradze.gymapplication.repository.TrainingTypeRepository;
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
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
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

        traineeRepository.save(trainee);

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

        updateTraineeProfile(trainee, updateRequestDto);

        TraineeProfileUpdateResponseDto responseDto = traineeMapper.traineeToUpdateResponseDto(trainee);

        responseDto.setTrainersList(getAllTrainerDto(username));

        return responseDto;
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        Trainee traineeToDelete = findTraineeProfileByUsername(username);
        traineeRepository.delete(traineeToDelete);
    }

    @Override
    @Transactional
    public void updateActiveStatus(String username, ActiveStatusDto activeStatusDto) {
        Trainee trainee = findTraineeProfileByUsername(username);
        trainee.setIsActive(activeStatusDto.getIsActive());
        traineeRepository.save(trainee);
    }

    @Override
    @Transactional
    public List<TrainingInfoDto> getTrainingsList(String username, TrainingSearchCriteria criteria) {
        trainingSearchValidator(username, criteria);

        List<Training> trainings = traineeRepository.getTrainingsList(
                username,
                criteria.getFrom(),
                criteria.getTo(),
                criteria.getName(),
                criteria.getTrainingTypeName());
        List<TrainingInfoDto> trainingInfoDtos = new ArrayList<>();

        for (Training t : trainings) {
            TrainingInfoDto trainingInfo = trainingMapper.trainingToTrainingInfoDto(t);

            trainingInfo.setUsername(t.getTrainer().getUsername());

            if (criteria.getTrainingTypeName() != null) {
                trainingInfo.setTrainingType(
                        trainingTypeMapper
                                .entityToDto(trainingTypeRepository.findByTrainingTypeName(criteria.getTrainingTypeName()))
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
        return traineeRepository
                .getAllTrainers(username)
                .stream()
                .map(trainerMapper::trainerToTrainerInfoDto)
                .toList();
    }

    private void updateTraineeProfile(Trainee trainee, TraineeProfileUpdateRequestDto updateRequestDto) {
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

        traineeRepository.save(trainee);
    }

    private Trainee findTraineeProfileByUsername(String username) {
        return getTrainee(username, traineeRepository);
    }

    static Trainee getTrainee(String username, TraineeRepository traineeRepository) {
        try {
            Trainee trainee = traineeRepository.findByUsername(username);
            if (trainee == null) {
                throw new GymException("Trainee not found with username: " + username, "404");
            }
            return trainee;
        } catch (Exception e) {
            throw new GymException("Trainee not found with username: " + username, "404");
        }
    }

    private boolean trainerExists(String username) {
        try {
            return trainerRepository.findByUsername(username) != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    private boolean trainingTypeExists(String typeName) {
        try {
            return trainingTypeRepository.findByTrainingTypeName(typeName) != null;
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
