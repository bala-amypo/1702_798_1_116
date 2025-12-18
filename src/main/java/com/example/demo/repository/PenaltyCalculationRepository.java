package com.example.demo.repository;

import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PenaltyCalculationRepository
        extends JpaRepository<PenaltyCalculation, Long> {

    // Latest penalty calculation
    Optional<PenaltyCalculation> findTopByContractOrderByCalculatedAtDesc(Contract contract);
}
