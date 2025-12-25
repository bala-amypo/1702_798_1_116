package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "breach_rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreachRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String ruleName;
    private BigDecimal penaltyPerDay;
    private Double maxPenaltyPercentage;
    private Boolean active;
    private Boolean isDefaultRule;
}