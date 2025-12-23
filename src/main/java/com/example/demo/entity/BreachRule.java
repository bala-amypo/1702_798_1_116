package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "breach_rules", uniqueConstraints = {
    @UniqueConstraint(columnNames = "ruleName")
})
public class BreachRule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Rule name is required")
    @Size(max = 100)
    @Column(unique = true, nullable = false)
    private String ruleName;
    
    @NotNull(message = "Penalty per day is required")
    @DecimalMin(value = "0.01", message = "Penalty per day must be greater than 0")
    private BigDecimal penaltyPerDay;
    
    @NotNull(message = "Maximum penalty percentage is required")
    @Min(value = 0, message = "Maximum penalty percentage must be between 0 and 100")
    @Max(value = 100, message = "Maximum penalty percentage must be between 0 and 100")
    private Double maxPenaltyPercentage;
    
    private Boolean active = true;
    
    @Column(name = "is_default_rule")
    private Boolean isDefaultRule = false;
    
    // Constructors
    public BreachRule() {}
    
    public BreachRule(String ruleName, BigDecimal penaltyPerDay, Double maxPenaltyPercentage) {
        this.ruleName = ruleName;
        this.penaltyPerDay = penaltyPerDay;
        this.maxPenaltyPercentage = maxPenaltyPercentage;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    
    public BigDecimal getPenaltyPerDay() { return penaltyPerDay; }
    public void setPenaltyPerDay(BigDecimal penaltyPerDay) { this.penaltyPerDay = penaltyPerDay; }
    
    public Double getMaxPenaltyPercentage() { return maxPenaltyPercentage; }
    public void setMaxPenaltyPercentage(Double maxPenaltyPercentage) { 
        this.maxPenaltyPercentage = maxPenaltyPercentage; 
    }
    
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    
    public Boolean getIsDefaultRule() { return isDefaultRule; }
    public void setIsDefaultRule(Boolean isDefaultRule) { this.isDefaultRule = isDefaultRule; }
}