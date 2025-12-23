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
        