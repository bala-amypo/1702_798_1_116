package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "penalty_calculations")
public class PenaltyCalculation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Contract is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
    
    @Min(value = 0, message = "Days delayed cannot be negative")
    private Integer daysDelayed;
    
    @NotNull(message = "Calculated penalty is required")
    @DecimalMin(value = "0.00", message = "Calculated penalty cannot be negative")
    private BigDecimal calculatedPenalty;
    
    @NotNull(message = "Applied rule is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_id", nullable = false)
    private BreachRule appliedRule;
    
    @CreationTimestamp
    private Timestamp calculatedAt;
    
    // Constructors
    public PenaltyCalculation() {}
    
    public PenaltyCalculation(Contract contract, Integer daysDelayed, 
                             BigDecimal calculatedPenalty, BreachRule appliedRule) {
        this.contract = contract;
        this.daysDelayed = daysDelayed;
        this.calculatedPenalty = calculatedPenalty;
        this.appliedRule = appliedRule;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }
    
    public Integer getDaysDelayed() { return daysDelayed; }
    public void setDaysDelayed(Integer daysDelayed) { this.daysDelayed = daysDelayed; }
    
    public BigDecimal getCalculatedPenalty() { return calculatedPenalty; }
    public void setCalculatedPenalty(BigDecimal calculatedPenalty) { 
        this.calculatedPenalty = calculatedPenalty; 
    }
    
    public BreachRule getAppliedRule() { return appliedRule; }
    public void setAppliedRule(BreachRule appliedRule) { this.appliedRule = appliedRule; }
    
    public Timestamp getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(Timestamp calculatedAt) { this.calculatedAt = calculatedAt; }
}