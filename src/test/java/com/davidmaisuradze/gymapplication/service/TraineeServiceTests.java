package com.davidmaisuradze.gymapplication.service;

import com.davidmaisuradze.gymapplication.dto.CredentialsDto;
import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import com.davidmaisuradze.gymapplication.entity.UserEntity;
import com.davidmaisuradze.gymapplication.exception.GymException;
import com.davidmaisuradze.gymapplication.mapper.TraineeMapper;
import com.davidmaisuradze.gymapplication.mapper.UserMapper;
import com.davidmaisuradze.gymapplication.repository.TraineeRepository;
import com.davidmaisuradze.gymapplication.service.impl.TraineeServiceImpl;
import com.davidmaisuradze.gymapplication.util.DetailsGenerator;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        when(traineeRepository.findByUsername(anyString())).thenThrow(NoResultException.class);

        GymException exception = assertThrows(GymException.class, () -> traineeService.getProfile(""));

        assertTrue(exception.getMessage().contains("Trainee not found with username: "));
        assertEquals("404", exception.getErrorCode());
    }
}
