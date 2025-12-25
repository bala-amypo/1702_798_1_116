package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String contractNumber;
    
    private String title;
    private String counterpartyName;
    private LocalDate agreedDeliveryDate;
    private BigDecimal baseContractValue;
    private String status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}