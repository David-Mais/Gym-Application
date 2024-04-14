package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainer.CreateTrainerDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.trainer.TrainerTrainingSearchDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainingtype.TrainingTypeDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.mapper.TraineeMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainerMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainingMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainingTypeMapper;
import com.davidmaisuradze.gymapplication.mapper.UserMapper;
import com.davidmaisuradze.gymapplication.repository.TraineeRepository;
import com.davidmaisuradze.gymapplication.repository.TrainerRepository;
import com.davidmaisuradze.gymapplication.repository.TrainingTypeRepository;
import com.davidmaisuradze.gymapplication.service.impl.TrainerServiceImpl;
import com.davidmaisuradze.gymapplication.util.DetailsGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
    @Mock
    private DetailsGenerator detailsGenerator;
    @Mock
    private TrainerMapper trainerMapper;
    @Mock
    private TraineeMapper traineeMapper;
    @Mock
    private UserMapper userMapper;
    @Mock
    private TrainingMapper trainingMapper;
    @Mock
    private TrainingTypeMapper trainingTypeMapper;
    @InjectMocks
    private TrainerServiceImpl trainerService;


    @Test
    void testCreateTrainer() {
        CreateTrainerDto createTraineeDto = new CreateTrainerDto();
        String firstName = "firstName";
        String lastName = "lastName";
        createTraineeDto.setFirstName(firstName);
        createTraineeDto.setLastName(lastName);
        String username = "user";
        String password = "password";
        TrainingTypeDto specialization = new TrainingTypeDto();
        specialization.setTrainingTypeName("specialization");

        createTraineeDto.setSpecialization(specialization);

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setUsername(username);
        credentialsDto.setPassword(password);

        when(detailsGenerator.generateUsername(anyString(), anyString())).thenReturn(username);
        when(detailsGenerator.generatePassword()).thenReturn(password);
        when(trainerMapper.createTrainerDtoToTrainer(any(CreateTrainerDto.class))).thenReturn(new Trainer());
        when(userMapper.userToCredentialsDto(any(UserEntity.class))).thenReturn(credentialsDto);
        when(trainingTypeRepository.findByTrainingTypeName(anyString())).thenReturn(Optional.of(new TrainingType()));

        CredentialsDto actual = trainerService.create(createTraineeDto);

        assertEquals(credentialsDto, actual);
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void testGetTraineeProfile() {
        String username = "user";
        Trainer trainer = new Trainer();
        trainer.setFirstName("firstName");
        trainer.setUsername(username);

        TrainerProfileDto trainerProfileDto = new TrainerProfileDto();
        trainerProfileDto.setFirstName("firstName");


        when(trainerRepository.findByUsername(any())).thenReturn(Optional.of(trainer));
        when(trainerMapper.trainerToTrainerProfileDto(any(Trainer.class))).thenReturn(trainerProfileDto);
        when(trainerRepository.getAllTrainees(anyString())).thenReturn(new ArrayList<>());

        TrainerProfileDto actual = trainerService.getProfile(username);

        assertEquals("firstName", actual.getFirstName());
        verify(trainerRepository, times(1)).findByUsername(anyString());
    }

    @Test
    void testUpdateProfile() {
        String username = "user";
        TrainerProfileUpdateRequestDto updateRequestDto = new TrainerProfileUpdateRequestDto();

        Trainer trainer = new Trainer();

        TrainerProfileUpdateResponseDto expectedResponse = new TrainerProfileUpdateResponseDto();


        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerMapper.trainerToUpdateResponseDto(any(Trainer.class))).thenReturn(expectedResponse);
        when(trainerRepository.getAllTrainees(anyString())).thenReturn(List.of(new Trainee()));

        TrainerProfileUpdateResponseDto actualResponse = trainerService.updateProfile(username, updateRequestDto);

        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse.getTraineesList());
        assertFalse(actualResponse.getTraineesList().isEmpty());

        verify(trainerRepository, times(1)).save(any(Trainer.class));
        verify(traineeMapper, times(1)).traineeToTraineeInfoDto(any(Trainee.class));
    }

    @Test
    void testUpdateActiveStatus() {
        ActiveStatusDto activeStatusDto = new ActiveStatusDto();
        activeStatusDto.setIsActive(true);

        Trainer trainer = new Trainer();
        trainer.setIsActive(false);

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));

        trainerService.updateActiveStatus(anyString(), activeStatusDto);

        assertEquals(true, trainer.getIsActive());
        verify(trainerRepository, times(1)).findByUsername(anyString());
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    void testTrainersNotAssigned() {
        Trainee trainee = new Trainee();

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(trainerRepository.getTrainersNotAssigned(anyString())).thenReturn(List.of(new Trainer()));
        when(trainerMapper.trainerToTrainerInfoDto(any(Trainer.class))).thenReturn(new TrainerInfoDto());

        List<TrainerInfoDto> actual = trainerService.getTrainersNotAssigned(anyString());

        assertFalse(actual.isEmpty());
    }

    @Test
    void testGetTrainingsList() {
        String username = "user";
        TrainerTrainingSearchDto criteria = new TrainerTrainingSearchDto();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();
        trainer.setSpecialization(TrainingType.builder().trainingTypeName("spec").build());

        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainee(trainee);

        List<Training> trainings = List.of(training);

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(trainer));
        when(trainerRepository.getTrainingsList(eq(username), isNull(), isNull(), isNull())).thenReturn(trainings);
        when(trainingMapper.trainingToTrainingInfoDto(any(Training.class))).thenReturn(new TrainingInfoDto());
        when(trainingTypeMapper.entityToDto(any(TrainingType.class))).thenReturn(new TrainingTypeDto());

        List<TrainingInfoDto> trainingDtos = trainerService.getTrainingsList(username, criteria);

        assertNotNull(trainingDtos);
        assertFalse(trainingDtos.isEmpty());

        verify(trainerRepository, times(1)).findByUsername(username);
        verify(trainerRepository, times(1)).getTrainingsList(eq(username), isNull(), isNull(), isNull());
    }

    @Test
    void testGetTrainer() {
        String username = "user";
        Trainer expected = new Trainer();
        expected.setUsername(username);

        when(trainerRepository.findByUsername(anyString())).thenReturn(Optional.of(expected));

        Trainer actual = trainerService.getTrainer(username);

        assertNotNull(actual);
        assertEquals(username, actual.getUsername());

        verify(trainerRepository, times(1)).findByUsername(anyString());
    }
}
