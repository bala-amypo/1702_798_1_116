// Entity: BreachRule.java
package com.example.demo.entity;

import lombok.*;
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
    
    @Column(nullable = false)
    private String ruleName;
    
    @Column(nullable = false)
    private BigDecimal penaltyPerDay;
    
    @Column(nullable = false)
    private Double maxPenaltyPercentage;
    
    @Column(nullable = false)
    private Boolean active;
    
    @Column(nullable = false)
    private Boolean isDefaultRule;
    
    @PrePersist
    protected void onCreate() {
        if (active == null) active = true;
        if (isDefaultRule == null) isDefaultRule = false;
    }
}