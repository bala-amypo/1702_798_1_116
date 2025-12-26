package com.example.demo.controller;

import com.example.demo.entity.Contract;
import com.example.demo.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@Tag(name = "Contract Management", description = "Contract CRUD operations")
@SecurityRequirement(name = "bearerAuth")
public class ContractController {

    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    @Operation(summary = "Create contract")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        Contract created = contractService.createContract(contract);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update contract")
    public ResponseEntity<Contract> updateContract(@PathVariable Long id, @RequestBody Contract contract) {
        Contract updated = contractService.updateContract(id, contract);
        return ResponseEntity.ok(updated);
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
    public ResponseEntity<Void> updateContractStatus(@PathVariable Long id) {
        contractService.updateContractStatus(id);
        return ResponseEntity.ok().build();
    }
}