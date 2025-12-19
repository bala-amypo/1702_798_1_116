package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class BreachReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contract contract;

    private Timestamp reportGeneratedAt;
    private Integer daysDelayed;
    private BigDecimal penaltyAmount;
    private String remarks;

    public BreachReport() {}

    /* ===== GETTERS & SETTERS ===== */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public Timestamp getReportGeneratedAt() { return reportGeneratedAt; }
    public void setReportGeneratedAt(Timestamp reportGeneratedAt) { this.reportGeneratedAt = reportGeneratedAt; }

    public Integer getDaysDelayed() { return daysDelayed; }
    public void setDaysDelayed(Integer daysDelayed) { this.daysDelayed = daysDelayed; }

    public BigDecimal getPenaltyAmount() { return penaltyAmount; }
    public void setPenaltyAmount(BigDecimal penaltyAmount) { this.penaltyAmount = penaltyAmount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    /* ===== BUILDER ===== */

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final BreachReport b = new BreachReport();

        public Builder contract(Contract v){ b.setContract(v); return this; }
        public Builder reportGeneratedAt(Timestamp v){ b.setReportGeneratedAt(v); return this; }
        public Builder daysDelayed(Integer v){ b.setDaysDelayed(v); return this; }
        public Builder penaltyAmount(BigDecimal v){ b.setPenaltyAmount(v); return this; }
        public Builder remarks(String v){ b.setRemarks(v); return this; }

        public BreachReport build(){ return b; }
    }
}
