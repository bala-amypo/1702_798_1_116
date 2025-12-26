package com.example.demo.controller;

import com.example.demo.dto.ContractDto;
import com.example.demo.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@Tag(name = "Contract Management", description = "Contract CRUD operations")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping
    @Operation(summary = "Get all contracts")
    public ResponseEntity<List<ContractDto>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get contract by ID")
    public ResponseEntity<ContractDto> getContractById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    @PostMapping
    @Operation(summary = "Create new contract")
    public ResponseEntity<ContractDto> createContract(@RequestBody ContractDto contractDto) {
        return ResponseEntity.ok(contractService.createContract(contractDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update contract")
    public ResponseEntity<ContractDto> updateContract(@PathVariable Long id, @RequestBody ContractDto contractDto) {
        return ResponseEntity.ok(contractService.updateContract(id, contractDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete contract")
    public ResponseEntity<Void> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}