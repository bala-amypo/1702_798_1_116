package com.example.demo.repository;

import com.example.demo.entity.Contract;
import java.util.List;
import java.util.Optional;

public interface ContractRepository {
    Contract save(Contract contract);
    Optional<Contract> findById(Long id);
    List<Contract> findAll();
    Optional<Contract> findByContractNumber(String contractNumber);
}