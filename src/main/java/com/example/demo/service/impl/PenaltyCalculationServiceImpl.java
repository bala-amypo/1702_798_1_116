package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.model.Contract.ContractStatus;
import com.example.demo.repository.*;
import com.example.demo.service.PenaltyCalculationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {
    
    private final PenaltyCalculationRepository penaltyCalculationRepository;
    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final BreachRuleRepository breachRuleRepository;
    
    public PenaltyCalculationServiceImpl(PenaltyCalculationRepository penaltyCalculationRepository,
                                        ContractRepository contractRepository,
                                        DeliveryRecordRepository deliveryRecordRepository,
                                        BreachRuleRepository breachRuleRepository) {
        this.penaltyCalculationRepository = penaltyCalculationRepository;
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
        this.breachRuleRepository = breachRuleRepository;
    }
    
    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {
        // Get contract
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + contractId));
        
        // Get latest delivery record
        DeliveryRecord latestDelivery = deliveryRecordRepository
            .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
            .orElseThrow(() -> new BadRequestException("No delivery record found for contract id: " + contractId));
        
        // Get active breach rule
        BreachRule activeRule = breachRuleRepository
            .findFirstByActiveTrueOrderByIsDefaultRuleDesc()
            .orElseThrow(() -> new BadRequestException("No active breach rule"));
        
        // Calculate days delayed
        Date agreedDate = contract.getAgreedDeliveryDate();
        Date actualDate = latestDelivery.getDeliveryDate();
        
        long diffInMillies = actualDate.getTime() - agreedDate.getTime();
        int daysDelayed = (int) (diffInMillies / (1000 * 60 * 60 * 24));
        
        if (daysDelayed < 0) {
            daysDelayed = 0;
        }
        
        // Calculate penalty
        BigDecimal dailyPenalty = activeRule.getPenaltyPerDay()
            .multiply(BigDecimal.valueOf(daysDelayed));
        
        BigDecimal maxPenalty = contract.getBaseContractValue()
            .multiply(BigDecimal.valueOf(activeRule.getMaxPenaltyPercentage() / 100.0));
        
        BigDecimal calculatedPenalty = dailyPenalty.min(maxPenalty);
        
        // Update contract status if breached
        if (daysDelayed > 0) {
            contract.setStatus(ContractStatus.BREACHED);
            contractRepository.save(contract);
        }
        
        // Create and save penalty calculation
        PenaltyCalculation calculation = new PenaltyCalculation();
        calculation.setContract(contract);
        calculation.setDaysDelayed(daysDelayed);
        calculation.setCalculatedPenalty(calculatedPenalty);
        calculation.setAppliedRule(activeRule);
        
        return penaltyCalculationRepository.save(calculation);
    }
    
    @Override
    public PenaltyCalculation getCalculationById(Long id) {
        return penaltyCalculationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Penalty calculation not found with id: " + id));
    }
    
    @Override
    public List<PenaltyCalculation> getCalculationsForContract(Long contractId) {
        if (!contractRepository.existsById(contractId)) {
            throw new ResourceNotFoundException("Contract not found with id: " + contractId);
        }
        return penaltyCalculationRepository.findByContractId(contractId);
    }
}