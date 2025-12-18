package com.example.demo.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "contracts",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "contract_number")
    }
)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_number", nullable = false, unique = true)
    private String contractNumber;

    @Column(nullable = false)
    private String title;

    @Column(name = "counterparty_name", nullable = false)
    private String counterpartyName;

    @Column(name = "agreed_delivery_date", nullable = false)
    private LocalDate agreedDeliveryDate;

    @Column(name = "base_contract_value", nullable = false)
    private BigDecimal baseContractValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ===== Lifecycle Hooks =====
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = Status.ACTIVE;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCounterpartyName() {
        return counterpartyName;
    }

    public void setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
    }

    public LocalDate getAgreedDeliveryDate() {
        return agreedDeliveryDate;
    }

    public void setAgreedDeliveryDate(LocalDate agreedDeliveryDate) {
        this.agreedDeliveryDate = agreedDeliveryDate;
    }

    public BigDecimal getBaseContractValue() {
        return baseContractValue;
    }

    public void setBaseContractValue(BigDecimal baseContractValue) {
        if (baseContractValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Base contract value must be greater than 0");
        }
        this.baseContractValue = baseContractValue;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
