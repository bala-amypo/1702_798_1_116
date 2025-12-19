package com.example.demo.service;

import com.example.demo.entity.PenaltyCalculation;

public interface PenaltyCalculationService {
    PenaltyCalculation calculatePenalty(Long contractId);
}
