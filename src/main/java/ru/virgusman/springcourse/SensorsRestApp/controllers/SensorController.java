package ru.virgusman.springcourse.SensorsRestApp.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import ru.virgusman.springcourse.SensorsRestApp.DTO.SensorDTO;
import ru.virgusman.springcourse.SensorsRestApp.services.SensorService;
import ru.virgusman.springcourse.SensorsRestApp.util.Errors.ErrorResponse;
import ru.virgusman.springcourse.SensorsRestApp.util.Errors.NotCreatedException;
import ru.virgusman.springcourse.SensorsRestApp.util.SensorValidator;

import static ru.virgusman.springcourse.SensorsRestApp.util.ErrorUtil.returnErrorsToClient;


@RestController
@RequestMapping("/sensors")
@Tag(name = "Контроллер сенсоров", description = "Управление сенсорами")
public class SensorController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;


    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    //Добавление нового сенсора
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult) {
        sensorValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        sensorService.save(sensorDTO);
        return ResponseEntity.ok(HttpStatus.OK);   //HTTP ответ с пустым телом и со статусом 200
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
