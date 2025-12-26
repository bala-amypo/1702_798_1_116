package com.example.demo.controller;

import com.example.demo.entity.BreachReport;
import com.example.demo.service.BreachReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/breach-reports")
@Tag(name = "Breach Report Management", description = "Breach report CRUD operations")
public class BreachReportController {

    @Autowired
    private BreachReportService breachReportService;

    @GetMapping
    @Operation(summary = "Get all breach reports")
    public ResponseEntity<List<BreachReport>> getAllBreachReports() {
        return ResponseEntity.ok(breachReportService.getAllBreachReports());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get breach report by ID")
    public ResponseEntity<BreachReport> getBreachReportById(@PathVariable Long id) {
        return ResponseEntity.ok(breachReportService.getBreachReportById(id));
    }

    @PostMapping
    @Operation(summary = "Create new breach report")
    public ResponseEntity<BreachReport> createBreachReport(@RequestBody BreachReport breachReport) {
        return ResponseEntity.ok(breachReportService.createBreachReport(breachReport));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update breach report")
    public ResponseEntity<BreachReport> updateBreachReport(@PathVariable Long id, @RequestBody BreachReport breachReport) {
        return ResponseEntity.ok(breachReportService.updateBreachReport(id, breachReport));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete breach report")
    public ResponseEntity<Void> deleteBreachReport(@PathVariable Long id) {
        breachReportService.deleteBreachReport(id);
        return ResponseEntity.noContent().build();
    }
}