package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "breach_reports")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreachReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
    
    private Integer daysDelayed;
    private BigDecimal penaltyAmount;
}