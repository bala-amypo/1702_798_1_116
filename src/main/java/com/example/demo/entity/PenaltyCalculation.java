package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "penalty_calculations")
public class PenaltyCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // One contract can have multiple penalty calculations (history)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(name = "days_delayed", nullable = false)
    private Integer daysDelayed;

    @Column(name = "calculated_penalty", nullable = false)
    private BigDecimal calculatedPenalty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applied_rule_id", nullable = false)
    private BreachRule appliedRule;

    @Column(name = "calculated_at", updatable = false)
    private LocalDateTime calculatedAt;

    // ===== Lifecycle Validation =====
    @PrePersist
    private void onCreate() {
        this.calculatedAt = LocalDateTime.now();

        // Rule: daysDelayed ≥ 0
        if (daysDelayed == null || daysDelayed < 0) {
            throw new IllegalArgumentException("Days delayed must be zero or positive");
        }

        // Rule: calculatedPenalty ≥ 0
        if (calculatedPenalty == null ||
                calculatedPenalty.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Calculated penalty must be zero or positive");
        }
    }

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Integer getDaysDelayed() {
        return daysDelayed;
    }

    public void setDaysDelayed(Integer daysDelayed) {
        if (daysDelayed < 0) {
            throw new IllegalArgumentException("Days delayed must be zero or positive");
        }
        this.daysDelayed = daysDelayed;
    }

    public BigDecimal getCalculatedPenalty() {
        return calculatedPenalty;
    }

    public void setCalculatedPenalty(BigDecimal calculatedPenalty) {
        if (calculatedPenalty.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Calculated penalty must be zero or positive");
        }
        this.calculatedPenalty = calculatedPenalty;
    }

    public BreachRule getAppliedRule() {
        return appliedRule;
    }

    public void setAppliedRule(BreachRule appliedRule) {
        this.appliedRule = appliedRule;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }
}
