package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateRequestDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingInfoDto;
import com.davidmaisuradze.gymapplication.dto.training.TrainingSearchCriteria;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.Trainer;
import com.davidmaisuradze.gymapplication.entity.Training;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.mapper.TraineeMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainerMapper;
import com.davidmaisuradze.gymapplication.mapper.TrainingMapper;
import com.davidmaisuradze.gymapplication.mapper.UserMapper;
import com.davidmaisuradze.gymapplication.repository.TraineeRepository;
import com.davidmaisuradze.gymapplication.service.impl.TraineeServiceImpl;
import com.davidmaisuradze.gymapplication.util.DetailsGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTests {
    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private DetailsGenerator detailsGenerator;
    @Mock
    private TraineeMapper traineeMapper;
    @Mock
    private TrainingMapper trainingMapper;
    @Mock
    private TrainerMapper trainerMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Test
    void createTrainee() {
        CreateTraineeDto createTraineeDto = new CreateTraineeDto();
        String firstName = "firstName";
        String lastName = "lastName";
        createTraineeDto.setFirstName(firstName);
        createTraineeDto.setLastName(lastName);
        String username = "user";
        String password = "password";

        CredentialsDto credentialsDto = new CredentialsDto();
        credentialsDto.setUsername(username);
        credentialsDto.setPassword(password);

        when(detailsGenerator.generatePassword()).thenReturn(password);
        when(detailsGenerator.generateUsername(anyString(), anyString())).thenReturn(username);
        when(traineeMapper.createTraineeDtoToTrainee(any(CreateTraineeDto.class))).thenReturn(new Trainee());
        when(userMapper.userToCredentialsDto(any(UserEntity.class))).thenReturn(credentialsDto);

        CredentialsDto actual = traineeService.create(createTraineeDto);

        assertEquals(actual, credentialsDto);
        verify(traineeRepository, times(1)).save(any(Trainee.class));
    }

    @Test
    void getTrainee() {
        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        GymException exception = assertThrows(GymException.class, () -> traineeService.getProfile(""));

        assertTrue(exception.getMessage().contains("Trainee not found with username: "));
        assertEquals("404", exception.getErrorCode());
    }

    @Test
    void testUpdateProfile() {
        String username = "user";
        TraineeProfileUpdateRequestDto updateRequestDto = new TraineeProfileUpdateRequestDto();

        Trainee trainee = new Trainee();

        TraineeProfileUpdateResponseDto expectedResponse = new TraineeProfileUpdateResponseDto();


        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(traineeMapper.traineeToUpdateResponseDto(any(Trainee.class))).thenReturn(expectedResponse);
        when(traineeRepository.getAllTrainers(anyString())).thenReturn(List.of(new Trainer()));

        TraineeProfileUpdateResponseDto actualResponse = traineeService.updateProfile(username, updateRequestDto);

        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse.getTrainersList());
        assertFalse(actualResponse.getTrainersList().isEmpty());

        verify(traineeRepository, times(1)).save(any(Trainee.class));
        verify(trainerMapper, times(1)).trainerToTrainerInfoDto(any(Trainer.class));
    }

    @Test
    void testDeleteTrainee() {
        String username = "user";
        Trainee traineeToDelete = new Trainee();
        traineeToDelete.setUsername(username);

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(traineeToDelete));

        traineeService.deleteByUsername(username);

        verify(traineeRepository, times(1)).findByUsername(anyString());
        verify(traineeRepository, times(1)).delete(traineeToDelete);
    }

    @Test
    void testUpdateActiveStatus() {
        ActiveStatusDto activeStatusDto = new ActiveStatusDto();
        activeStatusDto.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setIsActive(false);

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));

        traineeService.updateActiveStatus(anyString(), activeStatusDto);

        verify(traineeRepository, times(1)).findByUsername(anyString());
        assertEquals(true, trainee.getIsActive());
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void testGetTrainingsList() {
        String username = "user";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();

        Training training = new Training();
        training.setTrainer(trainer);

        List<Training> trainings = List.of(training);

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(trainee));
        when(traineeRepository.getTrainingsList(eq(username), isNull(), isNull(), isNull(), isNull())).thenReturn(trainings);
        when(trainingMapper.trainingToTrainingInfoDto(any(Training.class))).thenReturn(new TrainingInfoDto());

        List<TrainingInfoDto> trainingDtos = traineeService.getTrainingsList(username, criteria);

        assertNotNull(trainingDtos);
        assertFalse(trainingDtos.isEmpty());

        verify(traineeRepository, times(1)).findByUsername(username);
        verify(traineeRepository, times(1)).getTrainingsList(eq(username), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void testGetTrainee() {
        String username = "user";
        Trainee expected = new Trainee();
        expected.setUsername(username);

        when(traineeRepository.findByUsername(anyString())).thenReturn(Optional.of(expected));

        Trainee actual = traineeService.getTrainee(username);

        assertNotNull(actual);
        assertEquals(username, actual.getUsername());

        verify(traineeRepository, times(1)).findByUsername(anyString());
    }
}
