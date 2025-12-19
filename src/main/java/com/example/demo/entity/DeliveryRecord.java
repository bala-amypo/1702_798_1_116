package com.example.demo.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class DeliveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Contract contract;

    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    private String notes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public DeliveryRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public Date getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(Date deliveryDate) { this.deliveryDate = deliveryDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private final DeliveryRecord d = new DeliveryRecord();

        public Builder contract(Contract v){ d.setContract(v); return this; }
        public Builder deliveryDate(Date v){ d.setDeliveryDate(v); return this; }
        public Builder notes(String v){ d.setNotes(v); return this; }
        public Builder createdAt(Date v){ d.setCreatedAt(v); return this; }

        public DeliveryRecord build(){ return d; }
    }
}
