// Service Implementation: BreachReportServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.BreachReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BreachReportServiceImpl implements BreachReportService {
    
    private final BreachReportRepository breachReportRepository;
    private final PenaltyCalculationRepository penaltyCalculationRepository;
    private final ContractRepository contractRepository;
    
    @Override
    public BreachReport generateReport(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new RuntimeException("Contract not found with id: " + contractId));
            
        PenaltyCalculation calculation = penaltyCalculationRepository
            .findTopByContractIdOrderByCalculatedAtDesc(contractId)
            .orElseThrow(() -> new RuntimeException("No penalty calculation found for contract: " + contractId));
            
        BreachReport report = BreachReport.builder()
            .contract(contract)
            .calculation(calculation)
            .daysDelayed(calculation.getDaysDelayed())
            .penaltyAmount(calculation.getCalculatedPenalty())
            .build();
            
        return breachReportRepository.save(report);
    }
    
    @Override
    public BreachReport getReportById(Long id) {
        return breachReportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));
    }
    
    @Override
    public List<BreachReport> getReportsForContract(Long contractId) {
        return breachReportRepository.findByContractId(contractId);
    }
    
    @Override
    public List<BreachReport> getAllReports() {
        return breachReportRepository.findAll();
    }
}