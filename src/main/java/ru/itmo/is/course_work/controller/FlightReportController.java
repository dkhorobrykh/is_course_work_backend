package ru.itmo.is.course_work.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.course_work.model.dto.FlightReportDto;
import ru.itmo.is.course_work.service.FlightReportService;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class FlightReportController {

    @Autowired
    private FlightReportService flightReportService;

    @GetMapping("/flights/report/completed")
    public ResponseEntity<?> getCompletedFlightsReport() {
        List<FlightReportDto> report = flightReportService.generateCompletedFlightsReport();
        if (report.isEmpty()) {
            return ResponseEntity.ok("Завершённых рейсов нет");
        }
        return ResponseEntity.ok(report);
    }
}
