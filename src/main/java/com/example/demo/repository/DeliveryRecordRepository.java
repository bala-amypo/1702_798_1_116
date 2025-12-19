package com.example.demo.repository;

import com.example.demo.entity.DeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {

    List<DeliveryRecord> findByContractId(Long contractId);
}
