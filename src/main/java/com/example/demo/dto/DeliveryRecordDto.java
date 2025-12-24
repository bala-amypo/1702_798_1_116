package com.example.demo.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DeliveryRecordDto {
    private LocalDate deliveryDate;
    private String notes;
    private Long contractId;
}