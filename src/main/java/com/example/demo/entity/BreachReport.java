package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class BreachReport {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Contract contract;

    private Date reportGeneratedAt;

    private Integer daysDelayed;

    private BigDecimal penaltyAmount;

    private String remarks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Date getReportGeneratedAt() {
        return reportGeneratedAt;
    }

    public void setReportGeneratedAt(Date reportGeneratedAt) {
        this.reportGeneratedAt = reportGeneratedAt;
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
