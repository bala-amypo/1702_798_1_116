package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String contractNumber;
    private String title;
    private String counterpartyName;

    @Temporal(TemporalType.DATE)
    private Date agreedDeliveryDate;

    private BigDecimal baseContractValue;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Contract() {}

    /* ===== GETTERS & SETTERS ===== */
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

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    /* ===== BUILDER ===== */
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final Contract c = new Contract();

        public Builder contractNumber(String v){ c.setContractNumber(v); return this; }
        public Builder title(String v){ c.setTitle(v); return this; }
        public Builder counterpartyName(String v){ c.setCounterpartyName(v); return this; }
        public Builder agreedDeliveryDate(Date v){ c.setAgreedDeliveryDate(v); return this; }
        public Builder baseContractValue(BigDecimal v){ c.setBaseContractValue(v); return this; }
        public Builder status(String v){ c.setStatus(v); return this; }
        public Builder createdAt(Timestamp v){ c.setCreatedAt(v); return this; }
        public Builder updatedAt(Timestamp v){ c.setUpdatedAt(v); return this; }

        public Contract build(){ return c; }
    }
}
