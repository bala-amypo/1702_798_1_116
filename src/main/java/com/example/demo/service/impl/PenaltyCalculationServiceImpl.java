package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PenaltyCalculationRepository;
import com.example.demo.service.BreachRuleService;
import com.example.demo.service.PenaltyCalculationService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {

    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final BreachRuleService breachRuleService;
    private final PenaltyCalculationRepository penaltyCalculationRepository;

    // ✅ Constructor Injection (MANDATORY for tests)
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

        // 1️⃣ Fetch Contract
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Contract not found"));

        // 2️⃣ Fetch Latest Delivery Record (TEST REQUIRED METHOD)
        DeliveryRecord latestDelivery =
                deliveryRecordRepository
                        .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("No delivery record"));

        // 3️⃣ Fetch Active Breach Rule (TEST REQUIRED METHOD)
        BreachRule rule = breachRuleService.getActiveDefaultOrFirst();

        // 4️⃣ Calculate delay days
        LocalDate agreedDate = contract.getAgreedDeliveryDate();
        LocalDate actualDate = latestDelivery.getDeliveryDate();

        long daysDelayed = Math.max(0,
                ChronoUnit.DAYS.between(agreedDate, actualDate));

        // 5️⃣ Calculate penalty
        BigDecimal perDayPenalty = rule.getPenaltyPerDay();
        BigDecimal maxAllowedPenalty =
                contract.getBaseContractValue()
                        .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage()))
                        .divide(BigDecimal.valueOf(100));

        BigDecimal calculatedPenalty =
                perDayPenalty.multiply(BigDecimal.valueOf(daysDelayed));

        if (calculatedPenalty.compareTo(maxAllowedPenalty) > 0) {
            calculatedPenalty = maxAllowedPenalty;
        }

        // 6️⃣ Save calculation
        PenaltyCalculation calculation = PenaltyCalculation.builder()
                .contract(contract)
                .daysDelayed((int) daysDelayed)
                .calculatedPenalty(calculatedPenalty)
                .appliedRule(rule)
                .calculatedAt(Timestamp.from(Instant.now()))
                .build();

        return penaltyCalculationRepository.save(calculation);
    }

    @Override
    public PenaltyCalculation getCalculationById(Long id) {
        return penaltyCalculationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No penalty calculation"));
    }

    @Override
    public List<PenaltyCalculation> getCalculationsForContract(Long contractId) {
        return penaltyCalculationRepository.findByContractId(contractId);
    }
}
