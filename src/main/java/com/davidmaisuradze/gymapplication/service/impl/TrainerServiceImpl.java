package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainerDao;
import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
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
    }

    @Override
    public TrainerDto login(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        if (userDao.checkCredentials(username, password)) {
            Trainer trainer = trainerDao.findByUsername(username);
            return trainerMapper.entityToDto(trainer);
        }

        return null;
    }

    @Override
    public TrainerProfileDto getProfile(String username) {
        Trainer trainer = trainerDao.findByUsername(username);
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
        Trainer trainer = trainerDao.findByUsername(username);

        String firstName = requestDto.getFirstName();
        String lastName = requestDto.getLastName();
        TrainingType specialization = trainingTypeDao.findTrainingTypeByName(requestDto.getSpecialization().getTrainingTypeName());
        Boolean isActive = requestDto.getIsActive();

        if (firstName != null) {
            trainer.setFirstName(firstName);
        }
        if (lastName != null) {
            trainer.setLastName(lastName);
        }
        if (specialization != null) {
            trainer.setSpecialization(specialization);
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
    public boolean changePassword(String username, PasswordChangeDto passwordChangeDto) {
        String oldPassword = passwordChangeDto.getOldPassword();
        String newPassword = passwordChangeDto.getNewPassword();
        if (userDao.checkCredentials(username, oldPassword)) {
            log.info(CREDENTIAL_MATCH);
            Trainer trainer = trainerDao.findByUsername(username);
            trainer.setPassword(newPassword);
            log.info("Trainer password updated");
            trainerDao.update(trainer);
            return true;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return false;
    }

    @Override
    @Transactional
    public void activate(String username) {
        setActive(username, true);
    }

    @Override
    @Transactional
    public void deactivate(String username) {
        setActive(username, false);
    }

    @Override
    public List<TrainerInfoDto> getTrainersNotAssigned(String username) {
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

    private void setActive(String username, boolean isActive) {
        Trainer trainer = trainerDao.findByUsername(username);
        trainer.setIsActive(isActive);
        trainerDao.update(trainer);
        log.info("Trainer={} isActive={}", username, isActive);
    }

    private List<TraineeInfoDto> getAllTraineeInfoDto(String username) {
        return trainerDao.getAllTrainees(username)
                .stream()
                .map(traineeMapper::traineeToTraineeInfoDto)
                .toList();
    }
}
