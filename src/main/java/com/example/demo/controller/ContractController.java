package com.example.demo.controller;

import com.example.demo.model.Contract;
import com.example.demo.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {
    
    private final ContractService contractService;
    
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new contract")
    public ResponseEntity<Contract> createContract(@Valid @RequestBody Contract contract) {
        Contract createdContract = contractService.createContract(contract);
        return ResponseEntity.ok(createdContract);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing contract")
    public ResponseEntity<Contract> updateContract(@PathVariable Long id, 
                                                  @Valid @RequestBody Contract contract) {
        Contract updatedContract = contractService.updateContract(id, contract);
        return ResponseEntity.ok(updatedContract);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get contract by ID")
    public ResponseEntity<Contract> getContractById(@PathVariable Long id) {
        Contract contract = contractService.getContractById(id);
        return ResponseEntity.ok(contract);
    }
    
    @GetMapping
    @Operation(summary = "Get all contracts")
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }
    
    @PutMapping("/{id}/update-status")
    @Operation(summary = "Update contract status")
    public ResponseEntity<Contract> updateContractStatus(@PathVariable Long id) {
        Contract contract = contractService.updateContractStatus(id);
        return ResponseEntity.ok(contract);
    }
}