package com.example.demo.repository;

import com.example.demo.entity.PenaltyCalculation;
import java.util.List;
import java.util.Optional;

public interface PenaltyCalculationRepository {
    PenaltyCalculation save(PenaltyCalculation penaltyCalculation);
    Optional<PenaltyCalculation> findById(Long id);
    List<PenaltyCalculation> findByContractId(Long contractId);
    Optional<PenaltyCalculation> findTopByContractIdOrderByCalculatedAtDesc(Long contractId);
}