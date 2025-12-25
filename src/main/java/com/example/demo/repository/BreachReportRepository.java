package com.example.demo.repository;

import com.example.demo.entity.BreachReport;
import java.util.List;

public interface BreachReportRepository {
    BreachReport save(BreachReport breachReport);
    List<BreachReport> findAll();
    List<BreachReport> findByContractId(Long contractId);
}