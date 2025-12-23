package com.example.demo.controller;

import com.example.demo.model.BreachRule;
import com.example.demo.service.BreachRuleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/breach-rules")
public class BreachRuleController {
    
    private final BreachRuleService breachRuleService;
    
    public BreachRuleController(BreachRuleService breachRuleService) {
        this.breachRuleService = breachRuleService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new breach rule")
    public ResponseEntity<BreachRule> createRule(@Valid @RequestBody BreachRule rule) {
        BreachRule createdRule = breachRuleService.createRule(rule);
        return ResponseEntity.ok(createdRule);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing breach rule")
    public ResponseEntity<BreachRule> updateRule(@PathVariable Long id, 
                                                @Valid @RequestBody BreachRule rule) {
        BreachRule updatedRule = breachRuleService.updateRule(id, rule);
        return ResponseEntity.ok(updatedRule);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get breach rule by ID")
    public ResponseEntity<BreachRule> getRuleById(@PathVariable Long id) {
        BreachRule rule = breachRuleService.getRuleById(id);
        return ResponseEntity.ok(rule);
    }
    
    @GetMapping
    @Operation(summary = "Get all breach rules")
    public ResponseEntity<List<BreachRule>> getAllRules() {
        List<BreachRule> rules = breachRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
    
    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a breach rule")
    public ResponseEntity<BreachRule> deactivateRule(@PathVariable Long id) {
        BreachRule rule = breachRuleService.deactivateRule(id);
        return ResponseEntity.ok(rule);
    }
}