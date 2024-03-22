package ru.virgusman.springcourse.SensorsRestApp.DTO;

import java.util.List;

public class MeasurementsResponse {

    private List<MeasurementDTO> measurements;

    public MeasurementsResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurements() {
        return measurements;
    }

}
