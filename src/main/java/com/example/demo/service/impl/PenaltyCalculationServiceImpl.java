package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PenaltyCalculationRepository;
import com.example.demo.service.BreachRuleService;
import com.example.demo.service.PenaltyCalculationService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {

    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    private final BreachRuleService breachRuleService;
    private final PenaltyCalculationRepository penaltyCalculationRepository;

    // ✅ Constructor Injection (MANDATORY FOR TESTS)
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

        // 2️⃣ Fetch latest DeliveryRecord (TEST REQUIRED METHOD)
        DeliveryRecord latestDelivery =
                deliveryRecordRepository
                        .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
                        .orElseThrow(() ->
                                new BadRequestException("No delivery record"));

        // 3️⃣ Fetch active/default BreachRule
        BreachRule rule = breachRuleService.getActiveDefaultOrFirst();
        if (rule == null) {
            throw new BadRequestException("No active breach rule");
        }

        // 4️⃣ Calculate delay days
        long daysDelayed = ChronoUnit.DAYS.between(
                contract.getAgreedDeliveryDate(),
                latestDelivery.getDeliveryDate()
        );

        if (daysDelayed < 0) {
            daysDelayed = 0;
        }

        // 5️⃣ Calculate penalty
        BigDecimal perDayPenalty = rule.getPenaltyPerDay();

        BigDecimal calculatedPenalty =
                perDayPenalty.multiply(BigDecimal.valueOf(daysDelayed));

        BigDecimal maxAllowedPenalty =
                contract.getBaseContractValue()
                        .multiply(
                                BigDecimal.valueOf(rule.getMaxPenaltyPercentage())
                                        .divide(BigDecimal.valueOf(100))
                        );

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
