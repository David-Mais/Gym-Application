package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TraineeDao;
import com.davidmaisuradze.gymapplication.dao.UserDao;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.PasswordChangeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Training;
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
    public TraineeDto login(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        if (userDao.checkCredentials(username, password)) {
            Trainee trainee = traineeDao.findByUsername(username);
            return traineeMapper.entityToDto(trainee);
        }

        return null;
    }

    @Override
    public TraineeProfileDto getProfile(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainee trainee = traineeDao.findByUsername(username);
            TraineeProfileDto profileDto = traineeMapper.traineeToTrainerProfileDto(trainee);

            profileDto.setTrainersList(getAllTrainerDto(username));

            return profileDto;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return null;
    }

    @Override
    @Transactional
    public TraineeProfileUpdateResponseDto updateProfile(TraineeProfileUpdateRequestDto updateRequestDto) {
        String username = updateRequestDto.getUsername();
        String password = updateRequestDto.getPassword();

        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
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

        log.warn(CREDENTIAL_MISMATCH);
        return null;
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
    public void deleteByUsername(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);

            Trainee traineeToDelete = traineeDao.findByUsername(username);

            if (traineeToDelete == null) {
                log.warn("Trainee with username {} does not exist", username);
                return;
            }

            traineeDao.delete(traineeToDelete);
            log.info("Trainee with username: {} deleted", username);
        }
        log.warn(CREDENTIAL_MISMATCH);
    }

    @Override
    @Transactional
    public boolean changePassword(PasswordChangeDto passwordChangeDto) {
        String username = passwordChangeDto.getUsername();
        String oldPassword = passwordChangeDto.getOldPassword();
        boolean checked = userDao.checkCredentials(username, oldPassword);

        log.info("username password matched: {}", checked);

        if (checked) {
            log.info(CREDENTIAL_MATCH);

            Trainee trainee = traineeDao.findByUsername(username);

            trainee.setPassword(passwordChangeDto.getNewPassword());

            log.info("Trainee password updated");
            traineeDao.update(trainee);

            return true;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return false;
    }

    @Override
    @Transactional
    public Boolean activate(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        boolean isActive = true;
        return getaBoolean(username, password, isActive);
    }


    @Override
    @Transactional
    public Boolean deactivate(CredentialsDto credentialsDto) {
        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        boolean isActive = false;
        return getaBoolean(username, password, isActive);
    }

    @Override
    //issue
    public List<TrainingInfoDto> getTrainingsList(CredentialsDto credentialsDto, TrainingSearchCriteria criteria) {
        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);

            List<Training> trainings = traineeDao.getTrainingsList(criteria);

            List<TrainingInfoDto> trainingInfoDtos = new ArrayList<>();

            for (Training t : trainings) {
                TrainingInfoDto trainingInfo = trainingMapper.trainingToTrainingInfoDto(t);
                trainingInfo.setUsername(t.getTrainer().getUsername());
                trainingInfoDtos.add(trainingInfo);
            }

            return trainingInfoDtos;
        }
        return new ArrayList<>();
    }


    private Boolean getaBoolean(String username, String password, boolean isActive) {
        if (userDao.checkCredentials(username, password)) {
            log.info(CREDENTIAL_MATCH);
            Trainee trainee = traineeDao.findByUsername(username);
            trainee.setIsActive(isActive);
            log.info("Trainee={} isActive={}", username, isActive);
            return true;
        }
        log.warn(CREDENTIAL_MISMATCH);
        return false;
    }

    private List<TrainerInfoDto> getAllTrainerDto(String username) {
        return traineeDao
                .getAllTrainers(username)
                .stream()
                .map(trainerMapper::trainerToTrainerInfoDto)
                .toList();
    }
}
