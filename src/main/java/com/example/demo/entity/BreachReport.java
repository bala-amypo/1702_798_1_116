// Entity: BreachReport.java
package com.example.demo.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculation_id", nullable = false)
    private PenaltyCalculation calculation;
    
    @Column(nullable = false)
    private Integer daysDelayed;
    
    @Column(nullable = false)
    private BigDecimal penaltyAmount;
    
    @Column(updatable = false)
    private LocalDateTime generatedAt;
    
    @PrePersist
    protected void onCreate() {
        generatedAt = LocalDateTime.now();
    }
}