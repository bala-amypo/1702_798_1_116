package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "delivery_records")
public class DeliveryRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Contract is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
    
    @NotNull(message = "Delivery date is required")
    @PastOrPresent(message = "Delivery date cannot be in the future")
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    
    @NotBlank(message = "Notes are required")
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;
    
    // Constructors
    public DeliveryRecord() {}
    
    public DeliveryRecord(Contract contract, Date deliveryDate, String notes) {
        this.contract = contract;
        this.deliveryDate = deliveryDate;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }
    
    public Date getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}