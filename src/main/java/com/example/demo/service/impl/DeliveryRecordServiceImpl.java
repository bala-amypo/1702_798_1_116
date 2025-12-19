package com.example.demo.service.impl;

import com.example.demo.entity.DeliveryRecord;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.DeliveryRecordService;

import java.util.List;

public class DeliveryRecordServiceImpl implements DeliveryRecordService {

    private final DeliveryRecordRepository deliveryRecordRepository;

    public DeliveryRecordServiceImpl(DeliveryRecordRepository deliveryRecordRepository) {
        this.deliveryRecordRepository = deliveryRecordRepository;
    }

    @Override
    public DeliveryRecord logDelivery(DeliveryRecord record) {
        return deliveryRecordRepository.save(record);
    }

    @Override
    public List<DeliveryRecord> getDeliveriesForContract(Long contractId) {
        return deliveryRecordRepository.findByContractId(contractId);
    }
}
