package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.PenaltyCalculationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {
    
    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final BreachRuleRepository breachRuleRepository;
    private final PenaltyCalculationRepository penaltyCalculationRepository;
    
    public PenaltyCalculationServiceImpl(ContractRepository contractRepository,
                                         DeliveryRecordRepository deliveryRecordRepository,
                                         BreachRuleRepository breachRuleRepository,
                                         PenaltyCalculationRepository penaltyCalculationRepository) {
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
        this.breachRuleRepository = breachRuleRepository;
        this.penaltyCalculationRepository = penaltyCalculationRepository;
    }
    
    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + contractId));
        
        DeliveryRecord deliveryRecord = deliveryRecordRepository
                .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("No delivery record found for contract: " + contractId));
        
        BreachRule rule = breachRuleRepository.findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active breach rule found"));
        
        LocalDate agreedDate = contract.getAgreedDeliveryDate();
        LocalDate actualDate = deliveryRecord.getDeliveryDate();
        
        int daysDelayed = 0;
        if (actualDate.isAfter(agreedDate)) {
            daysDelayed = (int) ChronoUnit.DAYS.between(agreedDate, actualDate);
        }
        
        BigDecimal calculatedPenalty = calculatePenaltyAmount(
            daysDelayed, 
            rule.getPenaltyPerDay(), 
            rule.getMaxPenaltyPercentage(), 
            contract.getBaseContractValue()
        );
        
        PenaltyCalculation calculation = PenaltyCalculation.builder()
                .contract(contract)
                .daysDelayed(daysDelayed)
                .calculatedPenalty(calculatedPenalty)
                .build();
        
        return penaltyCalculationRepository.save(calculation);
    }
    
    private BigDecimal calculatePenaltyAmount(int daysDelayed, BigDecimal penaltyPerDay, 
                                              Double maxPercentage, BigDecimal contractValue) {
        BigDecimal dailyPenalty = penaltyPerDay.multiply(BigDecimal.valueOf(daysDelayed));
        
        BigDecimal maxPenalty = contractValue.multiply(BigDecimal.valueOf(maxPercentage / 100));
        
        return dailyPenalty.min(maxPenalty);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PenaltyCalculation getCalculationById(Long id) {
        return penaltyCalculationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calculation not found with id: " + id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PenaltyCalculation> getCalculationsForContract(Long contractId) {
        return penaltyCalculationRepository.findByContractId(contractId);
    }
}