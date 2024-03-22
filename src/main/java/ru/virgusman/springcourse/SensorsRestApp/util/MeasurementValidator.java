package ru.virgusman.springcourse.SensorsRestApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.virgusman.springcourse.SensorsRestApp.DTO.MeasurementDTO;
import ru.virgusman.springcourse.SensorsRestApp.models.Measurement;
import ru.virgusman.springcourse.SensorsRestApp.services.SensorService;

@Component
public class MeasurementValidator implements Validator {

    private final SensorService sensorService;

    public MeasurementValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;
        if (measurementDTO.getSensor() == null) {
            return;
        }
        if (sensorService.findOneByName(measurementDTO.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "Сенсор не найден!");
        }
    }
}
