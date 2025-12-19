package com.example.demo.service;

import com.example.demo.entity.BreachReport;
import java.util.List;

public interface BreachReportService {
    BreachReport generateReport(Long contractId);
    List<BreachReport> getReportsForContract(Long contractId);
}
