package com.example.demo.controller;

import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.service.PenaltyCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/penalty-calculations")
public class PenaltyCalculationController {
    
    @Autowired
    private PenaltyCalculationService penaltyCalculationService;
    
    @PostMapping("/contract/{contractId}")
    public ResponseEntity<PenaltyCalculation> calculatePenalty(@PathVariable Long contractId) {
        PenaltyCalculation calculation = penaltyCalculationService.calculatePenalty(contractId);
        return ResponseEntity.ok(calculation);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PenaltyCalculation> getCalculationById(@PathVariable Long id) {
        PenaltyCalculation calculation = penaltyCalculationService.getCalculationById(id);
        return ResponseEntity.ok(calculation);
    }
    
    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<PenaltyCalculation>> getCalculationsForContract(@PathVariable Long contractId) {
        List<PenaltyCalculation> calculations = penaltyCalculationService.getCalculationsForContract(contractId);
        return ResponseEntity.ok(calculations);
    }
}