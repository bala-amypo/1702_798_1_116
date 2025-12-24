// Entity: Contract.java
package com.example.demo.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contracts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String contractNumber;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String counterpartyName;
    
    @Column(nullable = false)
    private LocalDate agreedDeliveryDate;
    
    @Column(nullable = false)
    private BigDecimal baseContractValue;
    
    @Column(nullable = false)
    private String status;
    
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = "ACTIVE";
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}