package com.example.demo.controller;

import com.example.demo.model.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-records")
public class DeliveryRecordController {
    
    private final DeliveryRecordService deliveryRecordService;
    
    public DeliveryRecordController(DeliveryRecordService deliveryRecordService) {
        this.deliveryRecordService = deliveryRecordService;
    }
    
    @PostMapping
    @Operation(summary = "Create a new delivery record")
    public ResponseEntity<DeliveryRecord> createDeliveryRecord(@Valid @RequestBody DeliveryRecord record) {
        DeliveryRecord createdRecord = deliveryRecordService.createDeliveryRecord(record);
        return ResponseEntity.ok(createdRecord);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get delivery record by ID")
    public ResponseEntity<DeliveryRecord> getRecordById(@PathVariable Long id) {
        DeliveryRecord record = deliveryRecordService.getRecordById(id);
        return ResponseEntity.ok(record);
    }
    
    @GetMapping("/contract/{contractId}")
    @Operation(summary = "Get all delivery records for a contract")
    public ResponseEntity<List<DeliveryRecord>> getDeliveryRecordsForContract(@PathVariable Long contractId) {
        List<DeliveryRecord> records = deliveryRecordService.getDeliveryRecordsForContract(contractId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/contract/{contractId}/latest")
    @Operation(summary = "Get latest delivery record for a contract")
    public ResponseEntity<DeliveryRecord> getLatestDeliveryRecord(@PathVariable Long contractId) {
        DeliveryRecord record = deliveryRecordService.getLatestDeliveryRecord(contractId);
        return ResponseEntity.ok(record);
    }
}