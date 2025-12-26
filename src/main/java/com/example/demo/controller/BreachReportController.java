package com.example.demo.controller;

import com.example.demo.entity.BreachReport;
import com.example.demo.service.BreachReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Breach Report Management", description = "Breach report operations")
@SecurityRequirement(name = "bearerAuth")
public class BreachReportController {

    private final BreachReportService breachReportService;

    @Autowired
    public BreachReportController(BreachReportService breachReportService) {
        this.breachReportService = breachReportService;
    }

    @PostMapping("/generate/{contractId}")
    @Operation(summary = "Generate breach report for contract")
    public ResponseEntity<BreachReport> generateBreachReport(@PathVariable Long contractId) {
        BreachReport report = breachReportService.generateReport(contractId);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get breach report by ID")
    public ResponseEntity<String> getBreachReportById(@PathVariable Long id) {
        // This method is not available in the service interface
        return ResponseEntity.ok("Get by ID not implemented");
    }

    @GetMapping("/contract/{contractId}")
    @Operation(summary = "Get breach reports for contract")
    public ResponseEntity<List<BreachReport>> getBreachReportsForContract(@PathVariable Long contractId) {
        List<BreachReport> reports = breachReportService.getReportsForContract(contractId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping
    @Operation(summary = "Get all breach reports")
    public ResponseEntity<List<BreachReport>> getAllBreachReports() {
        List<BreachReport> reports = breachReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
}