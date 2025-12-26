// package com.example.demo.controller;

// import com.example.demo.dto.DeliveryRecordDto;
// import com.example.demo.service.DeliveryRecordService;
// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.tags.Tag;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/delivery-records")
// @Tag(name = "Delivery Record Management", description = "Delivery record CRUD operations")
// public class DeliveryRecordController {

//     @Autowired
//     private DeliveryRecordService deliveryRecordService;

//     @GetMapping
//     @Operation(summary = "Get all delivery records")
//     public ResponseEntity<List<DeliveryRecordDto>> getAllDeliveryRecords() {
//         return ResponseEntity.ok(deliveryRecordService.getAllDeliveryRecords());
//     }

//     @GetMapping("/{id}")
//     @Operation(summary = "Get delivery record by ID")
//     public ResponseEntity<DeliveryRecordDto> getDeliveryRecordById(@PathVariable Long id) {
//         return ResponseEntity.ok(deliveryRecordService.getDeliveryRecordById(id));
//     }

//     @PostMapping
//     @Operation(summary = "Create new delivery record")
//     public ResponseEntity<DeliveryRecordDto> createDeliveryRecord(@RequestBody DeliveryRecordDto deliveryRecordDto) {
//         return ResponseEntity.ok(deliveryRecordService.createDeliveryRecord(deliveryRecordDto));
//     }

//     @PutMapping("/{id}")
//     @Operation(summary = "Update delivery record")
//     public ResponseEntity<DeliveryRecordDto> updateDeliveryRecord(@PathVariable Long id, @RequestBody DeliveryRecordDto deliveryRecordDto) {
//         return ResponseEntity.ok(deliveryRecordService.updateDeliveryRecord(id, deliveryRecordDto));
//     }

//     @DeleteMapping("/{id}")
//     @Operation(summary = "Delete delivery record")
//     public ResponseEntity<Void> deleteDeliveryRecord(@PathVariable Long id) {
//         deliveryRecordService.deleteDeliveryRecord(id);
//         return ResponseEntity.noContent().build();
//     }
// }