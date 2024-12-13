package ru.itmo.is.course_work.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.is.course_work.model.InsuranceIssued;
import ru.itmo.is.course_work.model.dto.InsuranceIssueRequestDto;
import ru.itmo.is.course_work.model.dto.InsuranceIssuedDto;
import ru.itmo.is.course_work.model.mapper.InsuranceIssuedMapper;
import ru.itmo.is.course_work.service.InsuranceService;

import java.util.List;

@RestController
@RequestMapping("insurance")
@RequiredArgsConstructor
@Tag(
        name = "Insurance Controller",
        description = "Контроллер программ страхования"
)
public class InsuranceController {

    private final InsuranceService insuranceService;
    private final InsuranceIssuedMapper insuranceIssuedMapper;

    @GetMapping
    @PreAuthorize("@RoleService.hasAdminRole()")
    @Operation(summary = "Получить все оформленные страховки")
    public ResponseEntity<List<InsuranceIssuedDto>> getAllIssuedInsurances() {
        var result = insuranceService.getAllIssuedInsurances();

        return ResponseEntity.ok(insuranceIssuedMapper.toDto(result));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("@RoleService.hasAdminRole() || @RoleService.userIdEqualsCurrent(#userId)")
    @Operation(summary = "Получить все страховки, оформленные пользователем с [{userId}]")
    public ResponseEntity<List<InsuranceIssuedDto>> getAllIssuedInsuranceByUserId(@PathVariable Long userId) {
        var result = insuranceService.getAllIssuedInsurancesByRecipientId(userId);

        return ResponseEntity.ok(insuranceIssuedMapper.toDto(result));
    }

    @GetMapping("/flight/{flightId}")
    @PreAuthorize("@RoleService.hasAccessToFlight(@flightService.getFlightById(#flightId))")
    @Operation(summary = "Получить все страховки, оформленные на рейсе с [{flightId}]")
    public ResponseEntity<List<InsuranceIssuedDto>> getAllIssuedInsuranceByFlightId(@PathVariable Long flightId) {
        var result = insuranceService.getAllIssuedInsurancesByFlightId(flightId);

        return ResponseEntity.ok(insuranceIssuedMapper.toDto(result));
    }

    @GetMapping("/flight/{flightId}/user/{userId}")
    @PreAuthorize("(@RoleService.hasAdminRole() || @RoleService.userIdEqualsCurrent(#userId)) && @RoleService.hasAccessToFlight(@flightService.getFlightById(#flightId))")
    @Operation(summary = "Получить все страховки, оформленные на рейсе с [{flightId}] пользователем с [{userId}]")
    public ResponseEntity<List<InsuranceIssuedDto>> getAllIssuedInsuranceByFlightIdAndUserId(@PathVariable Long flightId, @PathVariable Long userId) {
        var result = insuranceService.getAllIssuedInsurancesByFlightIdAndRecipientId(flightId, userId);

        return ResponseEntity.ok(insuranceIssuedMapper.toDto(result));
    }

    @PostMapping
    @Operation(summary = "Добавить страховку к перелету")
    public ResponseEntity<InsuranceIssuedDto> addInsurance(@RequestBody @Valid InsuranceIssueRequestDto dto) {
        var result = insuranceService.issueNewInsurance(dto);

        return ResponseEntity.ok(insuranceIssuedMapper.toDto(result));
    }
}
