package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "contracts", uniqueConstraints = {
    @UniqueConstraint(columnNames = "contractNumber")
})
public class Contract {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Contract number is required")
    @Size(max = 100)
    @Column(unique = true, nullable = false)
    private String contractNumber;
    
    @NotBlank(message = "Title is required")
    @Size(max = 255)
    private String title;
    
    @NotBlank(message = "Counterparty name is required")
    @Size(max = 255)
    private String counterpartyName;
    
    @NotNull(message = "Agreed delivery date is required")
    @Temporal(TemporalType.DATE)
    private Date agreedDeliveryDate;
    
    @NotNull(message = "Base contract value is required")
    @DecimalMin(value = "0.01", message = "Base contract value must be greater than 0")
    private BigDecimal baseContractValue;
    
    @Enumerated(EnumType.STRING)
    private ContractStatus status = ContractStatus.ACTIVE;
    
    @CreationTimestamp
    private Timestamp createdAt;
    
    @UpdateTimestamp
    private Timestamp updatedAt;
    
    public enum ContractStatus {
        ACTIVE, COMPLETED, BREACHED
    }
    
    // Constructors
    public Contract() {}
    
    public Contract(String contractNumber, String title, String counterpartyName, 
                    Date agreedDeliveryDate, BigDecimal baseContractValue) {
        this.contractNumber = contractNumber;
        this.title = title;
        this.counterpartyName = counterpartyName;
        this.agreedDeliveryDate = agreedDeliveryDate;
        this.baseContractValue = baseContractValue;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getContractNumber() { return contractNumber; }
    public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getCounterpartyName() { return counterpartyName; }
    public void setCounterpartyName(String counterpartyName) { this.counterpartyName = counterpartyName; }
    
    public Date getAgreedDeliveryDate() { return agreedDeliveryDate; }
    public void setAgreedDeliveryDate(Date agreedDeliveryDate) { this.agreedDeliveryDate = agreedDeliveryDate; }
    
    public BigDecimal getBaseContractValue() { return baseContractValue; }
    public void setBaseContractValue(BigDecimal baseContractValue) { this.baseContractValue = baseContractValue; }
    
    public ContractStatus getStatus() { return status; }
    public void setStatus(ContractStatus status) { this.status = status; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}