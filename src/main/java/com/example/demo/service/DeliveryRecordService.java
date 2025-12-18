package com.example.demo.service;



import java.util.List;

public interface DeliveryRecordService {

    DeliveryRecord createDeliveryRecord(DeliveryRecord record);

    List<DeliveryRecord> getDeliveryRecordsForContract(Long contractId);

    // Throw exception if no delivery records found
    DeliveryRecord getLatestDeliveryRecord(Long contractId);

    DeliveryRecord getRecordById(Long id);
}
