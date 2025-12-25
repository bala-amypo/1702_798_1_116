package com.example.demo.service;

import com.example.demo.entity.Contract;
import java.util.List;

public interface ContractService {
    Contract createContract(Contract contract);
    Contract getContractById(Long id);
    Contract updateContract(Long id, Contract contract);
    List<Contract> getAllContracts();
    void updateContractStatus(Long id);
}