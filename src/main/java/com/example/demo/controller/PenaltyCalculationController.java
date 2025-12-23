package com.example.demo.controller;

import com.example.demo.model.PenaltyCalculation;
import com.example.demo.service.PenaltyCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/penalties")
public class PenaltyCalculationController {
    
    private final PenaltyCalculationService penaltyCalculationService;
    
    public PenaltyCalculationController(PenaltyCalculationService penaltyCalculationService) {
        this.penaltyCalculationService = penaltyCalculationService;
    }
    
    @PostMapping("/calculate/{contractId}")
    @Operation(summary = "Calculate penalty for a contract")
    public ResponseEntity<PenaltyCalculation> calculatePenalty(@PathVariable Long contractId) {
        PenaltyCalculation calculation = penaltyCalculationService.calculatePenalty(contractId);
        return ResponseEntity.ok(calculation);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get penalty calculation by ID")
    public ResponseEntity<PenaltyCalculation> getCalculationById(@PathVariable Long id) {
        PenaltyCalculation calculation = penaltyCalculationService.getCalculationById(id);
        return ResponseEntity.ok(calculation);
    }
    
    @GetMapping("/contract/{contractId}")
    @Operation(summary = "Get all penalty calculations for a contract")
    public ResponseEntity<List<PenaltyCalculation>> getCalculationsForContract(@PathVariable Long contractId) {
        List<PenaltyCalculation> calculations = penaltyCalculationService.getCalculationsForContract(contractId);
        return ResponseEntity.ok(calculations);
    }
}