package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {

    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final BreachRuleService breachRuleService;
    private final PenaltyCalculationRepository penaltyCalculationRepository;

    public PenaltyCalculationServiceImpl(
            ContractRepository contractRepository,
            DeliveryRecordRepository deliveryRecordRepository,
            BreachRuleService breachRuleService,
            PenaltyCalculationRepository penaltyCalculationRepository) {

        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
        this.breachRuleService = breachRuleService;
        this.penaltyCalculationRepository = penaltyCalculationRepository;
    }

    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {

        Contract contract =
                contractRepository.findById(contractId)
                        .orElseThrow(() -> new RuntimeException("Contract not found"));

        DeliveryRecord latestDelivery =
                deliveryRecordRepository
                        .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                        .orElseThrow(() -> new RuntimeException("No delivery record"));

        LocalDate agreedDate = contract.getAgreedDeliveryDate();
        LocalDate actualDate = latestDelivery.getDeliveryDate();

        long daysDelayed = Math.max(
                0,
                ChronoUnit.DAYS.between(agreedDate, actualDate)
        );

        BreachRule rule = breachRuleService.getActiveDefaultOrFirst();

        BigDecimal perDayPenalty = rule.getPenaltyPerDay();

        BigDecimal calculatedPenalty =
                perDayPenalty.multiply(BigDecimal.valueOf(daysDelayed));

        BigDecimal maxPenalty =
                contract.getBaseContractValue()
                        .multiply(
                                BigDecimal.valueOf(rule.getMaxPenaltyPercentage())
                                        .divide(BigDecimal.valueOf(100))
                        );

        if (calculatedPenalty.compareTo(maxPenalty) > 0) {
            calculatedPenalty = maxPenalty;
        }

        PenaltyCalculation pc = new PenaltyCalculation();
        pc.setContract(contract);
        pc.setDaysDelayed((int) daysDelayed);
        pc.setCalculatedPenalty(calculatedPenalty);
        pc.setAppliedRule(rule);
        pc.setCalculatedAt(new Timestamp(System.currentTimeMillis()));

        return penaltyCalculationRepository.save(pc);
    }

    @Override
    public PenaltyCalculation getCalculationById(Long id) {
        return penaltyCalculationRepository.findById(id).orElse(null);
    }

    @Override
    public List<PenaltyCalculation> getCalculationsForContract(Long contractId) {
        return penaltyCalculationRepository.findByContractId(contractId);
    }
}
