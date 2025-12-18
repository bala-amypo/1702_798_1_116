package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "delivery_records")
public class DeliveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many delivery records can belong to one contract
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @Column(length = 500)
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // ===== Lifecycle Hook =====
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();

        // Rule: deliveryDate cannot be future
        if (deliveryDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Delivery date cannot be in the future");
        }
    }

    // ===== Getters and Setters =====

    public Long getId() {
        return id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        if (deliveryDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Delivery date cannot be in the future");
        }
        this.deliveryDate = deliveryDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
