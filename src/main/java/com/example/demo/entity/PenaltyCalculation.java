package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class PenaltyCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contract contract;

    private Integer daysDelayed;
    private BigDecimal calculatedPenalty;

    @ManyToOne
    private BreachRule appliedRule;

    private Timestamp calculatedAt;

    public PenaltyCalculation() {}

    /* ===== GETTERS & SETTERS ===== */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public Integer getDaysDelayed() { return daysDelayed; }
    public void setDaysDelayed(Integer daysDelayed) { this.daysDelayed = daysDelayed; }

    public BigDecimal getCalculatedPenalty() { return calculatedPenalty; }
    public void setCalculatedPenalty(BigDecimal calculatedPenalty) { this.calculatedPenalty = calculatedPenalty; }

    public BreachRule getAppliedRule() { return appliedRule; }
    public void setAppliedRule(BreachRule appliedRule) { this.appliedRule = appliedRule; }

    public Timestamp getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(Timestamp calculatedAt) { this.calculatedAt = calculatedAt; }

    /* ===== BUILDER ===== */

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final PenaltyCalculation p = new PenaltyCalculation();

        public Builder contract(Contract v){ p.setContract(v); return this; }
        public Builder daysDelayed(Integer v){ p.setDaysDelayed(v); return this; }
        public Builder calculatedPenalty(BigDecimal v){ p.setCalculatedPenalty(v); return this; }
        public Builder appliedRule(BreachRule v){ p.setAppliedRule(v); return this; }
        public Builder calculatedAt(Timestamp v){ p.setCalculatedAt(v); return this; }

        public PenaltyCalculation build(){ return p; }
    }
}
