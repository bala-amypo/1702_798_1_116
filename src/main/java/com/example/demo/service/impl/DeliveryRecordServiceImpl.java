package com.example.demo.service.impl;

import com.example.demo.entity.DeliveryRecord;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.DeliveryRecordService;
import java.time.LocalDate;
import java.util.List;

public class DeliveryRecordServiceImpl implements DeliveryRecordService {
    private DeliveryRecordRepository deliveryRecordRepository;
    private ContractRepository contractRepository;

    @Override
    public DeliveryRecord createDeliveryRecord(DeliveryRecord deliveryRecord) {
        if (deliveryRecord.getDeliveryDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Delivery date cannot be in the future");
        }
        return deliveryRecordRepository.save(deliveryRecord);
    }

    @Override
    public DeliveryRecord getLatestDeliveryRecord(Long contractId) {
        return deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                .orElseThrow(() -> new RuntimeException("No delivery records found for contract: " + contractId));
    }

    @Override
    public DeliveryRecord getRecordById(Long id) {
        return deliveryRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery record not found with id: " + id));
    }

    @Override
    public List<DeliveryRecord> getDeliveryRecordsForContract(Long contractId) {
        return deliveryRecordRepository.findByContractIdOrderByDeliveryDateAsc(contractId);
    }
}