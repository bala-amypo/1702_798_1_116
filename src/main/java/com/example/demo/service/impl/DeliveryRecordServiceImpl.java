// Service Implementation: DeliveryRecordServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.DeliveryRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryRecordServiceImpl implements DeliveryRecordService {
    
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final ContractRepository contractRepository;
    
    @Override
    public DeliveryRecord createDeliveryRecord(DeliveryRecord record) {
        if (record.getDeliveryDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Delivery date cannot be in the future");
        }
        
        Contract contract = contractRepository.findById(record.getContract().getId())
            .orElseThrow(() -> new RuntimeException("Contract not found"));
        record.setContract(contract);
        
        return deliveryRecordRepository.save(record);
    }
    
    @Override
    public DeliveryRecord getRecordById(Long id) {
        return deliveryRecordRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Delivery record not found with id: " + id));
    }
    
    @Override
    public List<DeliveryRecord> getAllRecords() {
        return deliveryRecordRepository.findAll();
    }
    
    @Override
    public List<DeliveryRecord> getDeliveryRecordsForContract(Long contractId) {
        return deliveryRecordRepository.findByContractIdOrderByDeliveryDateAsc(contractId);
    }
    
    @Override
    public DeliveryRecord getLatestDeliveryRecord(Long contractId) {
        return deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(contractId)
            .orElseThrow(() -> new RuntimeException("No delivery records found for contract: " + contractId));
    }
}