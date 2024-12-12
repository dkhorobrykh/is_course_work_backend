package ru.itmo.is.course_work.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itmo.is.course_work.model.Cargo;
import ru.itmo.is.course_work.model.Flight;
import ru.itmo.is.course_work.model.Passenger;

import java.util.List;

@Data
@AllArgsConstructor
public class FlightAnalysisDto {
    private Flight flight;
    private List<Passenger> passengers;
    private List<Cargo> cargos;

    public int getPassengerCount() {
        return passengers.size();
    }

    public double getTotalCargoWeight() {
        return cargos.stream().mapToDouble(Cargo::getWeight).sum();
    }
}
