package com.example.demo.repository;

import com.example.demo.entity.DeliveryRecord;
import java.util.List;
import java.util.Optional;

public interface DeliveryRecordRepository {
    DeliveryRecord save(DeliveryRecord deliveryRecord);
    Optional<DeliveryRecord> findById(Long id);
    Optional<DeliveryRecord> findFirstByContractIdOrderByDeliveryDateDesc(Long contractId);
    List<DeliveryRecord> findByContractIdOrderByDeliveryDateAsc(Long contractId);
}