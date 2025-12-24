package com.example.demo.controller;

import com.example.demo.entity.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/delivery-records")
public class DeliveryRecordController {
    
    @Autowired
    private DeliveryRecordService deliveryRecordService;
    
    @PostMapping
    public ResponseEntity<DeliveryRecord> createDeliveryRecord(@Valid @RequestBody DeliveryRecord deliveryRecord) {
        DeliveryRecord created = deliveryRecordService.createDeliveryRecord(deliveryRecord);
        return ResponseEntity.ok(created);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryRecord> getRecordById(@PathVariable Long id) {
        DeliveryRecord record = deliveryRecordService.getRecordById(id);
        return ResponseEntity.ok(record);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryRecord> updateRecord(@PathVariable Long id, @RequestBody DeliveryRecord record) {
        DeliveryRecord updated = deliveryRecordService.updateRecord(id, record);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        deliveryRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<DeliveryRecord>> getRecordsForContract(@PathVariable Long contractId) {
        List<DeliveryRecord> records = deliveryRecordService.getDeliveryRecordsForContract(contractId);
        return ResponseEntity.ok(records);
    }
}