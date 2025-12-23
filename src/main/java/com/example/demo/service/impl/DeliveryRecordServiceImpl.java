package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Contract;
import com.example.demo.model.DeliveryRecord;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        if (record.getDeliveryDate().after(new Date())) {
            throw new BadRequestException("Delivery date cannot be in the future");
        }
        
        Contract contract = contractRepository.findById(record.getContract().getId())
            .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + record.getContract().getId()));
        
        record.setContract(contract);
        return deliveryRecordRepository.save(record);
    }
    
    @Override
    public List<DeliveryRecord> getDeliveryRecordsForContract(Long contractId) {
        if (!contractRepository.existsById(contractId)) {
            throw new ResourceNotFoundException("Contract not found with id: " + contractId);
        }
        return deliveryRecordRepository.findByContractIdOrderByDeliveryDateAsc(contractId);
    }
    
    @Override
    public DeliveryRecord getLatestDeliveryRecord(Long contractId) {
        return deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(contractId)
            .orElseThrow(() -> new BadRequestException("No delivery record found for contract id: " + contractId));
    }
    
    @Override
    public DeliveryRecord getRecordById(Long id) {
        return deliveryRecordRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Delivery record not found with id: " + id));
    }
}