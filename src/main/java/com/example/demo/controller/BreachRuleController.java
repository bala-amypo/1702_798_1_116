// package com.example.demo.controller;

// import com.example.demo.entity.BreachRule;
// import com.example.demo.service.BreachRuleService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/breach-rules")
// @Tag(name = "Breach Rule Management", description = "Breach rule CRUD operations")
// public class BreachRuleController {

//     @Autowired
//     private BreachRuleService breachRuleService;

//     @GetMapping
//     @Operation(summary = "Get all breach rules")
//     public ResponseEntity<List<BreachRule>> getAllBreachRules() {
//         return ResponseEntity.ok(breachRuleService.getAllBreachRules());
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get breach rule by ID")
//     public ResponseEntity<BreachRule> getBreachRuleById(@PathVariable Long id) {
//         return ResponseEntity.ok(breachRuleService.getBreachRuleById(id));
//     }

//     @PostMapping
//     @Operation(summary = "Create new breach rule")
//     public ResponseEntity<BreachRule> createBreachRule(@RequestBody BreachRule breachRule) {
//         return ResponseEntity.ok(breachRuleService.createBreachRule(breachRule));
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update breach rule")
//     public ResponseEntity<BreachRule> updateBreachRule(@PathVariable Long id, @RequestBody BreachRule breachRule) {
//         return ResponseEntity.ok(breachRuleService.updateBreachRule(id, breachRule));
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete breach rule")
//     public ResponseEntity<Void> deleteBreachRule(@PathVariable Long id) {
//         breachRuleService.deleteBreachRule(id);
//         return ResponseEntity.noContent().build();
//     }
// }