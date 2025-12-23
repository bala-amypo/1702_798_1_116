package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.BreachReport;
import com.example.demo.model.Contract;
import com.example.demo.model.PenaltyCalculation;
import com.example.demo.repository.BreachReportRepository;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.PenaltyCalculationRepository;
import com.example.demo.service.BreachReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BreachReportServiceImpl implements BreachReportService {
    
    private final BreachReportRepository breachReportRepository;
    private final ContractRepository contractRepository;
    private final PenaltyCalculationRepository penaltyCalculationRepository;
    
    public BreachReportServiceImpl(BreachReportRepository breachReportRepository,
                                  ContractRepository contractRepository,
                                  PenaltyCalculationRepository penaltyCalculationRepository) {
        this.breachReportRepository = breachReportRepository;
        this.contractRepository = contractRepository;
        this.penaltyCalculationRepository = penaltyCalculationRepository;
    }
    
    @Override
    public BreachReport generateReport(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + contractId));
        
        PenaltyCalculation latestCalculation = penaltyCalculationRepository
            .findTopByContractIdOrderByCalculatedAtDesc(contractId)
            .orElseThrow(() -> new BadRequestException("No penalty calculation found for contract id: " + contractId));
        
        BreachReport report = new BreachReport();
        report.setContract(contract);
        report.setDaysDelayed(latestCalculation.getDaysDelayed());
        report.setPenaltyAmount(latestCalculation.getCalculatedPenalty());
        report.setRemarks("Report generated for contract breach. Applied rule: " + 
                         latestCalculation.getAppliedRule().getRuleName());
        
        return breachReportRepository.save(report);
    }
    
    @Override
    public BreachReport getReportById(Long id) {
        return breachReportRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Breach report not found with id: " + id));
    }
    
    @Override
    public List<BreachReport> getReportsForContract(Long contractId) {
        if (!contractRepository.existsById(contractId)) {
            throw new ResourceNotFoundException("Contract not found with id: " + contractId);
        }
        return breachReportRepository.findByContractId(contractId);
    }
    
    @Override
    public List<BreachReport> getAllReports() {
        return breachReportRepository.findAll();
    }
}