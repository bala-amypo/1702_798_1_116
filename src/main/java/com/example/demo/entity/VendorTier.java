// Entity: VendorTier.java
package com.example.demo.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "vendor_tiers",
       uniqueConstraints = @UniqueConstraint(columnNames = {"tierName"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Tier name is required")
    @Column(nullable = false, unique = true)
    private String tierName;
    
    @NotNull(message = "Min threshold is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Min threshold must be greater than 0")
    @Column(nullable = false)
    private BigDecimal minThreshold;
    
    @NotNull(message = "Max threshold is required")
    @Column(nullable = false)
    private BigDecimal maxThreshold;
    
    @Column(nullable = false)
    private Double discountPercentage;
}