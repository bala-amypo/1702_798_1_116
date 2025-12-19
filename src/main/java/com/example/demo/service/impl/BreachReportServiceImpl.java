package com.example.demo.service.impl;

import com.example.demo.entity.BreachReport;
import com.example.demo.entity.PenaltyCalculation;
import com.example.demo.repository.BreachReportRepository;
import com.example.demo.repository.PenaltyCalculationRepository;
import com.example.demo.service.BreachReportService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BreachReportServiceImpl implements BreachReportService {

    private final BreachReportRepository breachReportRepository;
    private final PenaltyCalculationRepository penaltyCalculationRepository;

    public BreachReportServiceImpl(BreachReportRepository breachReportRepository,
                                   PenaltyCalculationRepository penaltyCalculationRepository) {
        this.breachReportRepository = breachReportRepository;
        this.penaltyCalculationRepository = penaltyCalculationRepository;
    }

    @Override
    public BreachReport generateReport(Long contractId) {

        PenaltyCalculation latest =
                penaltyCalculationRepository
                        .findTopByContractIdOrderByCalculatedAtDesc(contractId)
                        .orElseThrow(() -> new RuntimeException("No penalty calculation"));

        BreachReport report = new BreachReport();
        report.setContract(latest.getContract());
        report.setDaysDelayed(latest.getDaysDelayed());
        report.setPenaltyAmount(latest.getCalculatedPenalty());
        report.setReportGeneratedAt(new Timestamp(System.currentTimeMillis()));
        report.setRemarks("Auto generated breach report");

        return breachReportRepository.save(report);
    }

    @Override
    public BreachReport getReportById(Long id) {
        return breachReportRepository.findById(id).orElse(null);
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
