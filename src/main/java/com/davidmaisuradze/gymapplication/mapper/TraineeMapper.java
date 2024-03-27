package com.davidmaisuradze.gymapplication.mapper;

import com.davidmaisuradze.gymapplication.dto.trainee.CreateTraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeInfoDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileDto;
import com.davidmaisuradze.gymapplication.dto.trainee.TraineeProfileUpdateResponseDto;
import com.davidmaisuradze.gymapplication.entity.Trainee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TraineeMapper {
    TraineeMapper INSTANCE = Mappers.getMapper(TraineeMapper.class);
    TraineeDto entityToDto(Trainee trainee);
    Trainee dtoToEntity(TraineeDto traineeDto);
    Trainee createTraineeDtoToTrainee(CreateTraineeDto createTraineeDto);
    TraineeProfileDto traineeToTrainerProfileDto(Trainee trainee);
    TraineeProfileUpdateResponseDto traineeToUpdateResponseDto(Trainee trainee);
    TraineeInfoDto traineeToTraineeInfoDto(Trainee trainee);
}
