package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

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

        Contract contract = contractRepository.findById(contractId).orElse(null);
        if (contract == null) return null;

        List<DeliveryRecord> deliveries =
                deliveryRecordRepository.findByContractId(contractId);

        if (deliveries.isEmpty()) return null;

        DeliveryRecord latest = deliveries.get(deliveries.size() - 1);

        long delayDays = Math.max(0,
                latest.getDeliveredOn().toLocalDate()
                        .toEpochDay()
                        - contract.getDeliveryDate().toEpochDay());

        BreachRule rule = breachRuleService.getAllRules()
                .stream().filter(BreachRule::isActive).findFirst().orElse(null);

        BigDecimal penalty = BigDecimal.ZERO;

        if (rule != null) {
            penalty = BigDecimal.valueOf(delayDays)
                    .multiply(rule.getPenaltyPerDay());
        }

        PenaltyCalculation pc = PenaltyCalculation.builder()
                .contract(contract)
                .daysDelayed((int) delayDays)
                .calculatedPenalty(penalty)
                .appliedRule(rule)
                .calculatedAt(Timestamp.from(Instant.now()))
                .build();

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
