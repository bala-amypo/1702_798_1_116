package com.example.demo.service;

import com.example.demo.model.PenaltyCalculation;
import java.util.List;

public interface PenaltyCalculationService {
    PenaltyCalculation calculatePenalty(Long contractId);
    PenaltyCalculation getCalculationById(Long id);
    List<PenaltyCalculation> getCalculationsForContract(Long contractId);
}