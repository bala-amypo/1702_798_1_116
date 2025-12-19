package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class BreachRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ruleName;

    private BigDecimal penaltyPerDay;
    private Double maxPenaltyPercentage;
    private Boolean active;
    private Boolean isDefaultRule;

    public BreachRule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }

    public BigDecimal getPenaltyPerDay() { return penaltyPerDay; }
    public void setPenaltyPerDay(BigDecimal penaltyPerDay) { this.penaltyPerDay = penaltyPerDay; }

    public Double getMaxPenaltyPercentage() { return maxPenaltyPercentage; }
    public void setMaxPenaltyPercentage(Double maxPenaltyPercentage) { this.maxPenaltyPercentage = maxPenaltyPercentage; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Boolean getIsDefaultRule() { return isDefaultRule; }
    public void setIsDefaultRule(Boolean isDefaultRule) { this.isDefaultRule = isDefaultRule; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final BreachRule b = new BreachRule();

        public Builder ruleName(String v){ b.setRuleName(v); return this; }
        public Builder penaltyPerDay(BigDecimal v){ b.setPenaltyPerDay(v); return this; }
        public Builder maxPenaltyPercentage(Double v){ b.setMaxPenaltyPercentage(v); return this; }
        public Builder active(Boolean v){ b.setActive(v); return this; }
        public Builder isDefaultRule(Boolean v){ b.setIsDefaultRule(v); return this; }

        public BreachRule build(){ return b; }
    }
}
