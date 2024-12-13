package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Flight analysis controller",
        description = "Контроллер для анализа и мониторинга грузопотока и пассажиропотока"
)
public class FlowAnalysisController {

    private final FlowAnalysisService flowAnalysisService;

    @Operation(
            summary = "Возвращает полный анализ потока для всех рейсов."
    )
    @GetMapping
    public List<FlightAnalysisDto> getFlowAnalysis() {
        return flowAnalysisService.analyzeFlow();
    }

    @Operation(
            summary = "Возвращает сводную информацию о каждом рейсе (его название, количество пассажиров и общий вес груза)."
    )
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
