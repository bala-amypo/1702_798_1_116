package com.example.demo.controller;

import com.example.demo.model.BreachReport;
import com.example.demo.service.BreachReportService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class BreachReportController {
    
    private final BreachReportService breachReportService;
    
    public BreachReportController(BreachReportService breachReportService) {
        this.breachReportService = breachReportService;
    }
    
    @PostMapping("/generate/{contractId}")
    @Operation(summary = "Generate a breach report for a contract")
    public ResponseEntity<BreachReport> generateReport(@PathVariable Long contractId) {
        BreachReport report = breachReportService.generateReport(contractId);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get breach report by ID")
    public ResponseEntity<BreachReport> getReportById(@PathVariable Long id) {
        BreachReport report = breachReportService.getReportById(id);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/contract/{contractId}")
    @Operation(summary = "Get all breach reports for a contract")
    public ResponseEntity<List<BreachReport>> getReportsForContract(@PathVariable Long contractId) {
        List<BreachReport> reports = breachReportService.getReportsForContract(contractId);
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping
    @Operation(summary = "Get all breach reports")
    public ResponseEntity<List<BreachReport>> getAllReports() {
        List<BreachReport> reports = breachReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
}