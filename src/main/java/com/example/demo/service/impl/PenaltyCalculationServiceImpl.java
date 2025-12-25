package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PenaltyCalculationRepository;
import com.example.demo.service.PenaltyCalculationService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {
    private ContractRepository contractRepository;
    private DeliveryRecordRepository deliveryRecordRepository;
    private BreachRuleRepository breachRuleRepository;
    private PenaltyCalculationRepository penaltyCalculationRepository;

    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        
        DeliveryRecord latestRecord = deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                .orElseThrow(() -> new RuntimeException("No delivery record found"));
        
        BreachRule rule = breachRuleRepository.findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow(() -> new RuntimeException("No active breach rule found"));

        LocalDate agreedDate = contract.getAgreedDeliveryDate();
        LocalDate deliveryDate = latestRecord.getDeliveryDate();
        
        int daysDelayed = (int) Math.max(0, ChronoUnit.DAYS.between(agreedDate, deliveryDate));
        
        BigDecimal penalty = BigDecimal.ZERO;
        if (daysDelayed > 0) {
            penalty = rule.getPenaltyPerDay().multiply(BigDecimal.valueOf(daysDelayed));
            BigDecimal maxPenalty = contract.getBaseContractValue()
                    .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage()))
                    .divide(BigDecimal.valueOf(100));
            
            if (penalty.compareTo(maxPenalty) > 0) {
                penalty = maxPenalty;
            }
        }

        PenaltyCalculation calculation = PenaltyCalculation.builder()
                .contract(contract)
                .daysDelayed(daysDelayed)
                .calculatedPenalty(penalty)
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