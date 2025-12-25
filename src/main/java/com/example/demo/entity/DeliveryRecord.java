package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
    
    private LocalDate deliveryDate;
    private String notes;
}