package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "vendor_tiers", uniqueConstraints = {
    @UniqueConstraint(columnNames = "tierName")
})
public class VendorTier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Tier name is required")
    @Column(unique = true, nullable = false)
    private String tierName;
    
    @NotNull(message = "Minimum score threshold is required")
    @DecimalMin(value = "0.0", message = "Minimum score threshold cannot be negative")
    private Double minScoreThreshold;
    
    private Boolean active = true;
    
    // Constructors
    public VendorTier() {}
    
    public VendorTier(String tierName, Double minScoreThreshold) {
        this.tierName = tierName;
        this.minScoreThreshold = minScoreThreshold;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTierName() { return tierName; }
    public void setTierName(String tierName) { this.tierName = tierName; }
    
    public Double getMinScoreThreshold() { return minScoreThreshold; }
    public void setMinScoreThreshold(Double minScoreThreshold) { 
        this.minScoreThreshold = minScoreThreshold; 
    }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}