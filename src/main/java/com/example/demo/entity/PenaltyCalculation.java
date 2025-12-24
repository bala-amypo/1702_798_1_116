// Entity: PenaltyCalculation.java
package com.example.demo.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalty_calculations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PenaltyCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
    
    @Column(nullable = false)
    private Integer daysDelayed;
    
    @Column(nullable = false)
    private BigDecimal calculatedPenalty;
    
    @Column(updatable = false)
    private LocalDateTime calculatedAt;
    
    @PrePersist
    protected void onCreate() {
        calculatedAt = LocalDateTime.now();
        if (daysDelayed == null) daysDelayed = 0;
        if (calculatedPenalty == null) calculatedPenalty = BigDecimal.ZERO;
    }
}