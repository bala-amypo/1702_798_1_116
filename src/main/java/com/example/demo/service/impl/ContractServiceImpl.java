// Service Implementation: ContractServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    
    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    
    @Override
    public Contract createContract(Contract contract) {
        if (contract.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Base contract value must be greater than 0");
        }
        return contractRepository.save(contract);
    }
    
    @Override
    public Contract getContractById(Long id) {
        return contractRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Contract not found with id: " + id));
    }
    
    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
    
    @Override
    public Contract updateContract(Long id, Contract contract) {
        Contract existing = getContractById(id);
        
        if (contract.getTitle() != null) existing.setTitle(contract.getTitle());
        if (contract.getCounterpartyName() != null) existing.setCounterpartyName(contract.getCounterpartyName());
        if (contract.getAgreedDeliveryDate() != null) existing.setAgreedDeliveryDate(contract.getAgreedDeliveryDate());
        if (contract.getBaseContractValue() != null) {
            if (contract.getBaseContractValue().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Base contract value must be greater than 0");
            }
            existing.setBaseContractValue(contract.getBaseContractValue());
        }
        
        return contractRepository.save(existing);
    }
    
    @Override
    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
    
    @Override
    public Contract updateContractStatus(Long id) {
        Contract contract = getContractById(id);
        
        Optional<DeliveryRecord> latestRecord = deliveryRecordRepository
            .findFirstByContractIdOrderByDeliveryDateDesc(id);
            
        if (latestRecord.isEmpty()) {
            contract.setStatus("ACTIVE");
        } else {
            LocalDate deliveryDate = latestRecord.get().getDeliveryDate();
            if (deliveryDate.isAfter(contract.getAgreedDeliveryDate())) {
                contract.setStatus("BREACHED");
            } else {
                contract.setStatus("COMPLETED");
            }
        }
        
        return contractRepository.save(contract);
    }
}