package com.example.demo.service;

import com.example.demo.entity.DeliveryRecord;
import java.util.List;

public interface DeliveryRecordService {
    DeliveryRecord logDelivery(DeliveryRecord record);
    List<DeliveryRecord> getDeliveriesForContract(Long contractId);
}
