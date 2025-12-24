package com.example.demo.service.impl;

import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class DeliveryRecordServiceImpl implements DeliveryRecordService {
    
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final ContractRepository contractRepository;
    
    public DeliveryRecordServiceImpl(DeliveryRecordRepository deliveryRecordRepository,
                                     ContractRepository contractRepository) {
        this.deliveryRecordRepository = deliveryRecordRepository;
        this.contractRepository = contractRepository;
    }
    
    @Override
    public DeliveryRecord createDeliveryRecord(DeliveryRecord record) {
        if (record.getDeliveryDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Delivery date cannot be in the future");
        }
        
        if (record.getContract() == null || record.getContract().getId() == null) {
            throw new IllegalArgumentException("Contract must be specified");
        }
        
        Contract contract = contractRepository.findById(record.getContract().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
        record.setContract(contract);
        
        return deliveryRecordRepository.save(record);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DeliveryRecord getRecordById(Long id) {
        return deliveryRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery record not found with id: " + id));
    }
    
    @Override
    public DeliveryRecord updateRecord(Long id, DeliveryRecord record) {
        DeliveryRecord existing = getRecordById(id);
        existing.setDeliveryDate(record.getDeliveryDate());
        existing.setNotes(record.getNotes());
        return deliveryRecordRepository.save(existing);
    }
    
    @Override
    public void deleteRecord(Long id) {
        deliveryRecordRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryRecord> getDeliveryRecordsForContract(Long contractId) {
        return deliveryRecordRepository.findByContractIdOrderByDeliveryDateAsc(contractId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public DeliveryRecord getLatestDeliveryRecord(Long contractId) {
        return deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("No delivery records found for contract: " + contractId));
    }
}