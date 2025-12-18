package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "breach_reports")
public class BreachReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many reports can belong to one contract
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(name = "report_generated_at", updatable = false)
    private LocalDateTime reportGeneratedAt;

    @Column(name = "days_delayed", nullable = false)
    private Integer daysDelayed;

    @Column(name = "penalty_amount", nullable = false)
    private BigDecimal penaltyAmount;

    @Column(length = 500)
    private String remarks;

    // ===== Lifecycle Hook =====
    @PrePersist
    private void onCreate() {
        this.reportGeneratedAt = LocalDateTime.now();
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public LocalDateTime getReportGeneratedAt() {
        return reportGeneratedAt;
    }

    public Integer getDaysDelayed() {
        return daysDelayed;
    }

    public void setDaysDelayed(Integer daysDelayed) {
        this.daysDelayed = daysDelayed;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
