package com.example.demo.controller;

import com.example.demo.entity.BreachRule;
import com.example.demo.service.BreachRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/breach-rules")
public class BreachRuleController {
    
    @Autowired
    private BreachRuleService breachRuleService;
    
    @PostMapping
    public ResponseEntity<BreachRule> createRule(@Valid @RequestBody BreachRule breachRule) {
        BreachRule created = breachRuleService.createRule(breachRule);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BreachRule> getRuleById(@PathVariable Long id) {
        BreachRule rule = breachRuleService.getRuleById(id);
        return ResponseEntity.ok(rule);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BreachRule> updateRule(@PathVariable Long id, @Valid @RequestBody BreachRule breachRule) {
        BreachRule updated = breachRuleService.updateRule(id, breachRule);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        breachRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping
    public ResponseEntity<List<BreachRule>> getAllRules() {
        List<BreachRule> rules = breachRuleService.getAllRules();
        return ResponseEntity.ok(rules);
    }
}