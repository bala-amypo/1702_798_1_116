package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Contract;
import com.example.demo.model.Contract.ContractStatus;
import com.example.demo.repository.ContractRepository;
import com.example.demo.service.ContractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {
    
    private final ContractRepository contractRepository;
    
    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
    
    @Override
    public Contract createContract(Contract contract) {
        if (contractRepository.findByContractNumber(contract.getContractNumber()).isPresent()) {
            throw new BadRequestException("Contract number already exists");
        }
        
        contract.setStatus(ContractStatus.ACTIVE);
        return contractRepository.save(contract);
    }
    
    @Override
    public Contract updateContract(Long id, Contract contractDetails) {
        Contract contract = contractRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
        
        if (!contract.getContractNumber().equals(contractDetails.getContractNumber())) {
            if (contractRepository.findByContractNumber(contractDetails.getContractNumber()).isPresent()) {
                throw new BadRequestException("Contract number already exists");
            }
        }
        
        contract.setContractNumber(contractDetails.getContractNumber());
        contract.setTitle(contractDetails.getTitle());
        contract.setCounterpartyName(contractDetails.getCounterpartyName());
        contract.setAgreedDeliveryDate(contractDetails.getAgreedDeliveryDate());
        contract.setBaseContractValue(contractDetails.getBaseContractValue());
        
        return contractRepository.save(contract);
    }
    
    @Override
    public Contract getContractById(Long id) {
        return contractRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Contract not found with id: " + id));
    }
    
    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
    
    @Override
    public Contract updateContractStatus(Long contractId) {
        Contract contract = getContractById(contractId);
        // Status updates are handled by penalty calculation
        return contractRepository.save(contract);
    }
}