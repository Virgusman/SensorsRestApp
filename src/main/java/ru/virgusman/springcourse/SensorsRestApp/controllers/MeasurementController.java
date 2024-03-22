package ru.virgusman.springcourse.SensorsRestApp.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.virgusman.springcourse.SensorsRestApp.DTO.MeasurementDTO;
import ru.virgusman.springcourse.SensorsRestApp.DTO.MeasurementsResponse;
import ru.virgusman.springcourse.SensorsRestApp.models.Measurement;
import ru.virgusman.springcourse.SensorsRestApp.services.MeasurementService;
import ru.virgusman.springcourse.SensorsRestApp.util.Errors.ErrorResponse;
import ru.virgusman.springcourse.SensorsRestApp.util.Errors.NotCreatedException;
import ru.virgusman.springcourse.SensorsRestApp.util.MeasurementValidator;

import java.util.List;

import static ru.virgusman.springcourse.SensorsRestApp.util.ErrorUtil.returnErrorsToClient;

@RestController
@RequestMapping("/measurements")
@Tag(name = "Контроллер измерений", description = "Учёт измерений с сенсоров")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;

    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
    }

    //Добавление нового измерения
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> newMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        measurementValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
        measurementService.saveNewMeasurement(measurementDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //Вывод всех измерений
    @GetMapping
    public MeasurementsResponse getAllMeasurement(){
        return new MeasurementsResponse(measurementService.findAll());
    }

    //Возвращает количество дождливых записей из БД
    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementService.findAll().stream().filter(MeasurementDTO::getRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(NotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        //в HTTP овтете тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
