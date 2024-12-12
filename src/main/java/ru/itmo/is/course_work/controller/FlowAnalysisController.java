package ru.itmo.is.course_work.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.is.course_work.model.dto.FlightAnalysisDto;
import ru.itmo.is.course_work.service.FlowAnalysisService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flow-analysis")
@RequiredArgsConstructor
public class FlowAnalysisController {

    private final FlowAnalysisService flowAnalysisService;

    @GetMapping
    public List<FlightAnalysisDto> getFlowAnalysis() {
        return flowAnalysisService.analyzeFlow();
    }

    @GetMapping("/summary")
    public List<Map<String, Object>> getFlowAnalysisSummary() {
        List<FlightAnalysisDto> analysis = flowAnalysisService.analyzeFlow();
        return analysis.stream()
                .map(dto -> Map.of(
                        "flight", (Object) dto.getFlight().getName(),
                        "passengers", (Object) dto.getPassengerCount(),
                        "totalCargoWeight", (Object) dto.getTotalCargoWeight()
                ))
                .collect(Collectors.toList());
    }
}
