package com.example.demo.repository;

import com.example.demo.entity.PenaltyCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PenaltyCalculationRepository
        extends JpaRepository<PenaltyCalculation, Long> {

    List<PenaltyCalculation> findByContractId(Long contractId);
}
