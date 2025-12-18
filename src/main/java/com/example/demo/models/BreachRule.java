package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
    name = "breach_rules",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "rule_name")
    }
)
public class BreachRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_name", nullable = false, unique = true)
    private String ruleName;

    @Column(name = "penalty_per_day", nullable = false)
    private BigDecimal penaltyPerDay;

    @Column(name = "max_penalty_percentage", nullable = false)
    private Double maxPenaltyPercentage;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "is_default_rule", nullable = false)
    private Boolean isDefaultRule;

    // ===== Lifecycle Validation =====
    @PrePersist
    @PreUpdate
    private void validate() {

        // Rule: penaltyPerDay > 0
        if (penaltyPerDay == null || penaltyPerDay.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Penalty per day must be greater than 0");
        }

        // Rule: maxPenaltyPercentage between 0 and 100
        if (maxPenaltyPercentage == null ||
                maxPenaltyPercentage < 0 || maxPenaltyPercentage > 100) {
            throw new IllegalArgumentException(
                    "Max penalty percentage must be between 0 and 100");
        }

        if (active == null) {
            active = true;
        }

        if (isDefaultRule == null) {
            isDefaultRule = false;
        }
    }

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public BigDecimal getPenaltyPerDay() {
        return penaltyPerDay;
    }

    public void setPenaltyPerDay(BigDecimal penaltyPerDay) {
        this.penaltyPerDay = penaltyPerDay;
    }

    public Double getMaxPenaltyPercentage() {
        return maxPenaltyPercentage;
    }

    public void setMaxPenaltyPercentage(Double maxPenaltyPercentage) {
        this.maxPenaltyPercentage = maxPenaltyPercentage;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getIsDefaultRule() {
        return isDefaultRule;
    }

    public void setIsDefaultRule(Boolean isDefaultRule) {
        this.isDefaultRule = isDefaultRule;
    }
}
