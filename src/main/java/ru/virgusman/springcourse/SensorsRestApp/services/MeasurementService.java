package ru.virgusman.springcourse.SensorsRestApp.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.virgusman.springcourse.SensorsRestApp.DTO.MeasurementDTO;
import ru.virgusman.springcourse.SensorsRestApp.models.Measurement;
import ru.virgusman.springcourse.SensorsRestApp.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final ModelMapper modelMapper;
    private final SensorService sensorService;

    public MeasurementService(MeasurementRepository measurementRepository, ModelMapper modelMapper, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    //Сохранение нового измерения в БД
    @Transactional
    public void saveNewMeasurement(MeasurementDTO measurementDTO) {
        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    //Добавление к измерению зависимости "Сенсор" и время
    private void enrichMeasurement(Measurement measurement) {
        measurement.setSensor(sensorService.findOneByName(measurement.getSensor().getName()).orElse(null));
        measurement.setCreatedAt(LocalDateTime.now());
    }

    public List<MeasurementDTO> findAll() {
        return measurementRepository.findAll()
                .stream().map(n -> modelMapper.map(n, MeasurementDTO.class))
                .collect(Collectors.toList());
    }
}
