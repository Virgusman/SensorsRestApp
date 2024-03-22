package ru.virgusman.springcourse.SensorsRestApp.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.virgusman.springcourse.SensorsRestApp.DTO.SensorDTO;
import ru.virgusman.springcourse.SensorsRestApp.models.Sensor;
import ru.virgusman.springcourse.SensorsRestApp.repositories.SensorRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    private final ModelMapper modelMapper;

    public SensorService(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    //Сохранение сенсора в БД
    @Transactional
    public void save(SensorDTO sensorDTO) {
        sensorRepository.save(modelMapper.map(sensorDTO, Sensor.class));
    }

    //Используется в валидации (наличие сенсора в БД)
    public Optional<Sensor> findOneByName(String name) {
        return sensorRepository.findByName(name);
    }
}
