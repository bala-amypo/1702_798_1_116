package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "breach_reports")
public class BreachReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Contract is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
    
    @CreationTimestamp
    private Timestamp reportGeneratedAt;
    
    @Min(value = 0, message = "Days delayed cannot be negative")
    private Integer daysDelayed;
    
    @NotNull(message = "Penalty amount is required")
    @DecimalMin(value = "0.00", message = "Penalty amount cannot be negative")
    private BigDecimal penaltyAmount;
    
    @Column(columnDefinition = "TEXT")
    private String remarks;
    
    // Constructors
    public BreachReport() {}
    
    public BreachReport(Contract contract, Integer daysDelayed, 
                       BigDecimal penaltyAmount, String remarks) {
        this.contract = contract;
        this.daysDelayed = daysDelayed;
        this.penaltyAmount = penaltyAmount;
        this.remarks = remarks;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }
    
    public Timestamp getReportGeneratedAt() { return reportGeneratedAt; }
    public void setReportGeneratedAt(Timestamp reportGeneratedAt) { 
        this.reportGeneratedAt = reportGeneratedAt; 
    }
    
    public Integer getDaysDelayed() { return daysDelayed; }
    public void setDaysDelayed(Integer daysDelayed) { this.daysDelayed = daysDelayed; }
    
    public BigDecimal getPenaltyAmount() { return penaltyAmount; }
    public void setPenaltyAmount(BigDecimal penaltyAmount) { this.penaltyAmount = penaltyAmount; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}