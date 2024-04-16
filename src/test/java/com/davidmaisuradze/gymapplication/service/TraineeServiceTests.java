package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.ActiveStatusDto;
import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
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
import com.davidmaisuradze.gymapplication.repository.TrainerRepository;
import com.davidmaisuradze.gymapplication.repository.TrainingTypeRepository;
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
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;
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
    void testCreateTrainee_WhenValidCreateDtoProvided_ThenReturnCredentialsDto() {
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
    void testCreateTrainee_WhenInvalidCreateDtoProvided_ThenThrowGymException() {
        CreateTraineeDto createTraineeDto = new CreateTraineeDto();
        createTraineeDto.setFirstName(null);
        createTraineeDto.setLastName("lastName");

        when(detailsGenerator.generatePassword()).thenReturn(null);
        when(detailsGenerator.generateUsername(null, "lastName")).thenReturn(null);

        when(traineeMapper.createTraineeDtoToTrainee(any(CreateTraineeDto.class))).thenReturn(new Trainee());
        when(userMapper.userToCredentialsDto(any(UserEntity.class))).thenThrow(new RuntimeException("No user should be created"));

        assertThrows(RuntimeException.class, () -> traineeService.create(createTraineeDto));
    }

    @Test
    void testGetTraineeProfile_WhenUsernameExists_ThenReturnTrainee() {
        String username = "Some.User";
        String firstName = "firstName";
        String lastName = "lastName";
        Trainee expected = new Trainee();
        expected.setUsername(username);
        expected.setFirstName(firstName);
        expected.setLastName(lastName);

        TraineeProfileDto profileDto = new TraineeProfileDto();
        profileDto.setFirstName(firstName);
        profileDto.setLastName(lastName);

        when(traineeRepository.findByUsernameIgnoreCase(username)).thenReturn(Optional.of(expected));

        Trainee actual = traineeService.getTrainee(username);

        assertNotNull(actual);
        assertEquals(firstName, actual.getFirstName());
        assertEquals(lastName, actual.getLastName());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }

    @Test
    void testGetTraineeProfile_WhenUsernameDoesNotExist_ThenThrowGymException() {
        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        GymException exception = assertThrows(GymException.class, () -> traineeService.getProfile(""));

        assertTrue(exception.getMessage().contains("Trainee not found with username: "));
        assertEquals("404", exception.getErrorCode());
    }

    @Test
    void testUpdateProfile_WhenUserExists_ThenReturnTraineeUpdateResponseDto() {
        String username = "user";
        TraineeProfileUpdateRequestDto updateRequestDto = new TraineeProfileUpdateRequestDto();

        Trainee trainee = new Trainee();

        TraineeProfileUpdateResponseDto expectedResponse = new TraineeProfileUpdateResponseDto();


        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(trainee));
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
    void testUpdateProfile_WhenUserDoesNotExist_ThenThrowGymException() {
        String username = "user";

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        TraineeProfileUpdateRequestDto updateRequestDto = new TraineeProfileUpdateRequestDto();

        GymException exception = assertThrows(
                GymException.class,
                () -> traineeService.updateProfile(username, updateRequestDto));

        assertTrue(exception.getMessage().contains("Trainee not found with username: "));
        assertEquals("404", exception.getErrorCode());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }

    @Test
    void testDeleteTrainee_WhenTraineeExists() {
        String username = "user";
        Trainee traineeToDelete = new Trainee();
        traineeToDelete.setUsername(username);

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(traineeToDelete));

        traineeService.deleteByUsername(username);

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
        verify(traineeRepository, times(1)).delete(traineeToDelete);
    }

    @Test
    void testDeleteTrainee_WhenTraineeDoesNotExist_ThenThrowGymException() {
        String username = "Non.Existing";

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        GymException exception = assertThrows(
                GymException.class,
                () -> traineeService.deleteByUsername(username)
        );

        assertTrue(exception.getMessage().contains("Trainee not found with username: "));
        assertEquals("404", exception.getErrorCode());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }

    @Test
    void testUpdateActiveStatus() {
        ActiveStatusDto activeStatusDto = new ActiveStatusDto();
        activeStatusDto.setIsActive(true);

        Trainee trainee = new Trainee();
        trainee.setIsActive(false);

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(trainee));

        traineeService.updateActiveStatus(anyString(), activeStatusDto);

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
        assertEquals(true, trainee.getIsActive());
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    void testUpdateActiveStatus_WhenTraineeDoesNotExist_ThenThrowGymException() {
        String username = "Non.Existing";
        ActiveStatusDto statusDto = new ActiveStatusDto();

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        GymException exception = assertThrows(
                GymException.class,
                () -> traineeService.updateActiveStatus(username, statusDto)
        );

        assertTrue(exception.getMessage().contains("Trainee not found with username: "));
        assertEquals("404", exception.getErrorCode());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }

    @Test
    void testGetTrainingsList_WhenTraineeExists_ThenReturnTrainingsList() {
        String username = "user";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        Trainee trainee = new Trainee();
        Trainer trainer = new Trainer();

        Training training = new Training();
        training.setTrainer(trainer);

        List<Training> trainings = List.of(training);

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(trainee));
        when(traineeRepository.getTrainingsList(eq(username), isNull(), isNull(), isNull(), isNull())).thenReturn(trainings);
        when(trainingMapper.trainingToTrainingInfoDto(any(Training.class))).thenReturn(new TrainingInfoDto());

        List<TrainingInfoDto> trainingDtos = traineeService.getTrainingsList(username, criteria);

        assertNotNull(trainingDtos);
        assertFalse(trainingDtos.isEmpty());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(username);
        verify(traineeRepository, times(1)).getTrainingsList(eq(username), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void testGetTrainingsList_WhenTrainingTypeDoesNotExist_ThenThrowGymException() {
        String username = "user";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setTrainingTypeName("nonexisting");
        Trainee trainee = new Trainee();

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(trainee));
        when(trainingTypeRepository.findByTrainingTypeNameIgnoreCase(anyString())).thenReturn(Optional.empty());

        GymException exception = assertThrows(
                GymException.class,
                () -> traineeService.getTrainingsList(username, criteria)
        );

        assertTrue(exception.getMessage().contains("Training type not found with name: "));
        assertEquals("404", exception.getErrorCode());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }

    @Test
    void testGetTrainingsList_WhenTrainerDoesNotExist_ThenThrowGymException() {
        String username = "user";
        TrainingSearchCriteria criteria = new TrainingSearchCriteria();
        criteria.setName("nonexisting");
        Trainee trainee = new Trainee();

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(trainee));
        when(trainerRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        GymException exception = assertThrows(
                GymException.class,
                () -> traineeService.getTrainingsList(username, criteria)
        );

        assertTrue(exception.getMessage().contains("Trainer not found with username: "));
        assertEquals("404", exception.getErrorCode());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }

    @Test
    void testGetTrainee_WhenTraineeExists_ThenReturnTrainee() {
        String username = "user";
        Trainee expected = new Trainee();
        expected.setUsername(username);

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.of(expected));

        Trainee actual = traineeService.getTrainee(username);

        assertNotNull(actual);
        assertEquals(username, actual.getUsername());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }

    @Test
    void testGetTrainee_WhenTraineeDoesNotExist_ThenThrowGymException() {
        String username = "user";
        Trainee expected = new Trainee();
        expected.setUsername(username);

        when(traineeRepository.findByUsernameIgnoreCase(anyString())).thenReturn(Optional.empty());

        GymException exception = assertThrows(
                GymException.class,
                () -> traineeService.getTrainee(username)
        );

        assertTrue(exception.getMessage().contains("Trainee not found with username: "));
        assertEquals("404", exception.getErrorCode());

        verify(traineeRepository, times(1)).findByUsernameIgnoreCase(anyString());
    }
}
