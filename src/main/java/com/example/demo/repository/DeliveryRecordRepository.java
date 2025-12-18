package com.example.demo.repository;

import com.example.demo.entity.DeliveryRecord;
import com.example.demo.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {

    // Latest delivery record for breach calculation
    Optional<DeliveryRecord> findTopByContractOrderByDeliveryDateDesc(Contract contract);
}
