package com.example.demo.controller;

import com.example.demo.entity.BreachRule;
import com.example.demo.service.BreachRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/breach-rules")
@Tag(name = "Breach Rule Management", description = "Breach rule operations")
@SecurityRequirement(name = "bearerAuth")
public class BreachRuleController {

    private final BreachRuleService breachRuleService;

    @Autowired
    public BreachRuleController(BreachRuleService breachRuleService) {
        this.breachRuleService = breachRuleService;
    }

    @PostMapping
    @Operation(summary = "Create breach rule")
    public ResponseEntity<BreachRule> createBreachRule(@RequestBody BreachRule breachRule) {
        BreachRule created = breachRuleService.createRule(breachRule);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update breach rule")
    public ResponseEntity<BreachRule> updateBreachRule(@PathVariable Long id, @RequestBody BreachRule breachRule) {
        BreachRule updated = breachRuleService.updateRule(id, breachRule);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get breach rule by ID")
    public ResponseEntity<BreachRule> getBreachRuleById(@PathVariable Long id) {
        BreachRule rule = breachRuleService.getRuleById(id);
        return ResponseEntity.ok(rule);
    }

    @GetMapping
    @Operation(summary = "Get all breach rules")
    public ResponseEntity<List<BreachRule>> getAllBreachRules() {
        List<BreachRule> rules = breachRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate breach rule")
    public ResponseEntity<Void> deactivateBreachRule(@PathVariable Long id) {
        breachRuleService.deactivateRule(id);
        return ResponseEntity.ok().build();
    }
}