package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class BreachReportServiceImpl implements BreachReportService {

    private final BreachReportRepository breachReportRepository;
    private final PenaltyCalculationService penaltyCalculationService;

    public BreachReportServiceImpl(
            BreachReportRepository breachReportRepository,
            PenaltyCalculationService penaltyCalculationService) {

        this.breachReportRepository = breachReportRepository;
        this.penaltyCalculationService = penaltyCalculationService;
    }

    @Override
    public BreachReport generateReport(Long contractId) {

        PenaltyCalculation pc =
                penaltyCalculationService.getCalculationsForContract(contractId)
                        .stream().findFirst().orElse(null);

        if (pc == null) return null;

        BreachReport report = BreachReport.builder()
                .contract(pc.getContract())
                .daysDelayed(pc.getDaysDelayed())
                .penaltyAmount(pc.getCalculatedPenalty())
                .reportGeneratedAt(Timestamp.from(Instant.now()))
                .remarks("Auto generated report")
                .build();

        return breachReportRepository.save(report);
    }

    @Override
    public List<BreachReport> getReportsForContract(Long contractId) {
        return breachReportRepository.findByContractId(contractId);
    }
}
