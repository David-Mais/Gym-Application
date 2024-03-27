package com.davidmaisuradze.gymapplication.service.impl;

import com.davidmaisuradze.gymapplication.dao.TrainingTypeDao;
import com.davidmaisuradze.gymapplication.dto.trainingtype.TrainingTypeDto;
import com.davidmaisuradze.gymapplication.entity.TrainingType;
import com.davidmaisuradze.gymapplication.mapper.TrainingTypeMapper;
import com.davidmaisuradze.gymapplication.service.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingTypeServiceImpl implements TrainingTypeService {
    private final TrainingTypeDao trainingTypeDao;
    private final TrainingTypeMapper trainingTypeMapper;

    @Override
    public TrainingTypeDto findTrainingTypeByName(String name) {
        TrainingType trainingType = trainingTypeDao.findTrainingTypeByName(name);
        return trainingTypeMapper.entityToDto(trainingType);
    }

    @Override
    public List<TrainingTypeDto> findAll() {
        List<TrainingTypeDto> trainingTypes = trainingTypeDao.findAll()
                .stream()
                .map(trainingTypeMapper::entityToDto)
                .toList();
        log.info("TrainingTypes mapped to dtos");
        return trainingTypes;
    }
}
