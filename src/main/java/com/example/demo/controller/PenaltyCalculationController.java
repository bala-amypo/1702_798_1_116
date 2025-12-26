package com.example.demo.controller;

import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.service.PenaltyCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penalties")
@Tag(name = "Penalty Calculation Management", description = "Penalty calculation operations")
@SecurityRequirement(name = "bearerAuth")
public class PenaltyCalculationController {

    private final PenaltyCalculationService penaltyCalculationService;

    @Autowired
    public PenaltyCalculationController(PenaltyCalculationService penaltyCalculationService) {
        this.penaltyCalculationService = penaltyCalculationService;
    }

    @PostMapping("/calculate/{contractId}")
    @Operation(summary = "Calculate penalty for contract")
    public ResponseEntity<PenaltyCalculation> calculatePenalty(@PathVariable Long contractId) {
        PenaltyCalculation calculation = penaltyCalculationService.calculatePenalty(contractId);
        return ResponseEntity.ok(calculation);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get penalty calculation by ID")
    public ResponseEntity<PenaltyCalculation> getPenaltyCalculationById(@PathVariable Long id) {
        PenaltyCalculation calculation = penaltyCalculationService.getCalculationById(id);
        return ResponseEntity.ok(calculation);
    }

    @GetMapping("/contract/{contractId}")
    @Operation(summary = "Get penalty calculations for contract")
    public ResponseEntity<List<PenaltyCalculation>> getPenaltyCalculationsForContract(@PathVariable Long contractId) {
        List<PenaltyCalculation> calculations = penaltyCalculationService.getCalculationsForContract(contractId);
        return ResponseEntity.ok(calculations);
    }
}