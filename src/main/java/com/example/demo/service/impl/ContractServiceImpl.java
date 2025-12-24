package com.example.demo.service.impl;

import com.example.demo.entity.Contract;
import com.example.demo.entity.DeliveryRecord;
import com.example.demo.repository.ContractRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.service.ContractService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
    
    private final ContractRepository contractRepository;
    private final DeliveryRecordRepository deliveryRecordRepository;
    
    public ContractServiceImpl(ContractRepository contractRepository, 
                               DeliveryRecordRepository deliveryRecordRepository) {
        this.contractRepository = contractRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
    }
    
    @Override
    public Contract createContract(Contract contract) {
        if (contract.getBaseContractValue() == null || 
            contract.getBaseContractValue().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Base contract value must be positive");
        }
        return contractRepository.save(contract);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Contract getContractById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
    }
    
    @Override
    public Contract updateContract(Long id, Contract contract) {
        Contract existing = getContractById(id);
        existing.setTitle(contract.getTitle());
        existing.setCounterpartyName(contract.getCounterpartyName());
        existing.setAgreedDeliveryDate(contract.getAgreedDeliveryDate());
        existing.setBaseContractValue(contract.getBaseContractValue());
        return contractRepository.save(existing);
    }
    
    @Override
    public void deleteContract(Long id) {
        contractRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
    
    @Override
    public void updateContractStatus(Long contractId) {
        Contract contract = getContractById(contractId);
        LocalDate today = LocalDate.now();
        
        Optional<DeliveryRecord> latestRecord = 
            deliveryRecordRepository.findFirstByContractIdOrderByDeliveryDateDesc(contractId);
        
        if (latestRecord.isPresent()) {
            LocalDate deliveryDate = latestRecord.get().getDeliveryDate();
            if (deliveryDate.isAfter(contract.getAgreedDeliveryDate())) {
                contract.setStatus("BREACHED");
            } else {
                contract.setStatus("FULFILLED");
            }
        } else {
            if (today.isAfter(contract.getAgreedDeliveryDate())) {
                contract.setStatus("BREACHED");
            } else {
                contract.setStatus("ACTIVE");
            }
        }
        
        contractRepository.save(contract);
    }
}