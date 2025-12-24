package com.example.demo.controller;

import com.example.demo.entity.BreachReport;
import com.example.demo.service.BreachReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/breach-reports")
public class BreachReportController {
    
    @Autowired
    private BreachReportService breachReportService;
    
    @PostMapping("/contract/{contractId}")
    public ResponseEntity<BreachReport> generateReport(@PathVariable Long contractId) {
        BreachReport report = breachReportService.generateReport(contractId);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BreachReport> getReportById(@PathVariable Long id) {
        BreachReport report = breachReportService.getReportById(id);
        return ResponseEntity.ok(report);
    }
    
    @GetMapping
    public ResponseEntity<List<BreachReport>> getAllReports() {
        List<BreachReport> reports = breachReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<BreachReport>> getReportsForContract(@PathVariable Long contractId) {
        List<BreachReport> reports = breachReportService.getReportsForContract(contractId);
        return ResponseEntity.ok(reports);
    }
}