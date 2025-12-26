// package com.example.demo.controller;

// import com.example.demo.entity.PenaltyCalculation;
// import com.example.demo.service.PenaltyCalculationService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/penalty-calculations")
// @Tag(name = "Penalty Calculation Management", description = "Penalty calculation CRUD operations")
// public class PenaltyCalculationController {

//     @Autowired
//     private PenaltyCalculationService penaltyCalculationService;

//     @GetMapping
//     @Operation(summary = "Get all penalty calculations")
//     public ResponseEntity<List<PenaltyCalculation>> getAllPenaltyCalculations() {
//         return ResponseEntity.ok(penaltyCalculationService.getAllPenaltyCalculations());
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get penalty calculation by ID")
//     public ResponseEntity<PenaltyCalculation> getPenaltyCalculationById(@PathVariable Long id) {
//         return ResponseEntity.ok(penaltyCalculationService.getPenaltyCalculationById(id));
//     }

//     @PostMapping
//     @Operation(summary = "Create new penalty calculation")
//     public ResponseEntity<PenaltyCalculation> createPenaltyCalculation(@RequestBody PenaltyCalculation penaltyCalculation) {
//         return ResponseEntity.ok(penaltyCalculationService.createPenaltyCalculation(penaltyCalculation));
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update penalty calculation")
//     public ResponseEntity<PenaltyCalculation> updatePenaltyCalculation(@PathVariable Long id, @RequestBody PenaltyCalculation penaltyCalculation) {
//         return ResponseEntity.ok(penaltyCalculationService.updatePenaltyCalculation(id, penaltyCalculation));
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete penalty calculation")
//     public ResponseEntity<Void> deletePenaltyCalculation(@PathVariable Long id) {
//         penaltyCalculationService.deletePenaltyCalculation(id);
//         return ResponseEntity.noContent().build();
//     }
// }