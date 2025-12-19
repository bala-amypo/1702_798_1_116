package com.example.demo.repository;

import com.example.demo.entity.DeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {

    // REQUIRED BY TESTS
    Optional<DeliveryRecord> findFirstByContractIdOrderByDeliveryDateDesc(Long contractId);

    // REQUIRED BY TESTS
    List<DeliveryRecord> findByContractIdOrderByDeliveryDateAsc(Long contractId);
}
