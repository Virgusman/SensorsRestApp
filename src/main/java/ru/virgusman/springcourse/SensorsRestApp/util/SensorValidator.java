package ru.virgusman.springcourse.SensorsRestApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.virgusman.springcourse.SensorsRestApp.DTO.SensorDTO;
import ru.virgusman.springcourse.SensorsRestApp.models.Sensor;
import ru.virgusman.springcourse.SensorsRestApp.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    //Проверка на наличие сенсора в базе по названию
    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;
        if (sensorService.findOneByName(sensor.getName()).isPresent()) {
            errors.rejectValue("name", "", "Такой сенсор уже зарегистрирован!");
        }
    }
}
