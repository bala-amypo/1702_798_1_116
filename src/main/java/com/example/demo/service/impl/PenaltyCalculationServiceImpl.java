// Service Implementation: PenaltyCalculationServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.PenaltyCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {
    
    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final BreachRuleRepository breachRuleRepository;
    private final PenaltyCalculationRepository penaltyCalculationRepository;
    
    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new RuntimeException("Contract not found with id: " + contractId));
            
        DeliveryRecord deliveryRecord = deliveryRecordRepository
            .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
            .orElseThrow(() -> new RuntimeException("No delivery record found for contract: " + contractId));
            
        BreachRule rule = breachRuleRepository.findFirstByActiveTrueOrderByIsDefaultRuleDesc()
            .orElseThrow(() -> new RuntimeException("No active breach rule found"));
            
        long daysDelayed = ChronoUnit.DAYS.between(
            contract.getAgreedDeliveryDate(),
            deliveryRecord.getDeliveryDate()
        );
        
        if (daysDelayed < 0) daysDelayed = 0;
        
        BigDecimal dailyPenalty = rule.getPenaltyPerDay();
        BigDecimal maxPenalty = contract.getBaseContractValue()
            .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage() / 100));
        
        BigDecimal calculatedPenalty = dailyPenalty.multiply(BigDecimal.valueOf(daysDelayed));
        
        if (calculatedPenalty.compareTo(maxPenalty) > 0) {
            calculatedPenalty = maxPenalty;
        }
        
        PenaltyCalculation calculation = PenaltyCalculation.builder()
            .contract(contract)
            .daysDelayed((int) daysDelayed)
            .calculatedPenalty(calculatedPenalty)
            .build();
            
        return penaltyCalculationRepository.save(calculation);
    }
    
    @Override
    public PenaltyCalculation getCalculationById(Long id) {
        return penaltyCalculationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Calculation not found with id: " + id));
    }
    
    @Override
    public List<PenaltyCalculation> getCalculationsForContract(Long contractId) {
        return penaltyCalculationRepository.findByContractId(contractId);
    }
}