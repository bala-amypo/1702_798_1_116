package com.example.demo.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "breach_rules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreachRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rule_name", nullable = false, unique = true)
    private String ruleName;

    @Column(name = "penalty_per_day", nullable = false)
    private BigDecimal penaltyPerDay;

    @Column(name = "max_penalty_percentage", nullable = false)
    private Double maxPenaltyPercentage;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "is_default_rule", nullable = false)
    private Boolean isDefaultRule;
}