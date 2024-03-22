package ru.virgusman.springcourse.SensorsRestApp.DTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
    @Min(value = -100, message = "Значение не может быть меньше, чем -100")
    @Max(value = 100, message = "Значение не может быть больше, чем 100")
    @NotNull(message = "Значение не может быть пустым")
    private Double value;

    @NotNull(message = "Значение должно быть true или false")
    private Boolean raining;

    @NotNull(message = "Должен быть указан сенсор!")
    private SensorDTO sensor;
}
