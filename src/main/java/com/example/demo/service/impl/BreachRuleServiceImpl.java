package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.BreachRule;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.service.BreachRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BreachRuleServiceImpl implements BreachRuleService {
    
    private final BreachRuleRepository breachRuleRepository;
    
    public BreachRuleServiceImpl(BreachRuleRepository breachRuleRepository) {
        this.breachRuleRepository = breachRuleRepository;
    }
    
    @Override
    public BreachRule createRule(BreachRule rule) {
        if (rule.getIsDefaultRule() != null && rule.getIsDefaultRule()) {
            // Deactivate other default rules
            List<BreachRule> existingDefaults = breachRuleRepository.findAll()
                .stream()
                .filter(r -> r.getIsDefaultRule() != null && r.getIsDefaultRule() && r.getActive())
                .toList();
            
            for (BreachRule existing : existingDefaults) {
                existing.setIsDefaultRule(false);
                breachRuleRepository.save(existing);
            }
        }
        
        return breachRuleRepository.save(rule);
    }
    
    @Override
    public BreachRule updateRule(Long id, BreachRule ruleDetails) {
        BreachRule rule = getRuleById(id);
        
        if (!rule.getRuleName().equals(ruleDetails.getRuleName())) {
            throw new BadRequestException("Rule name cannot be changed");
        }
        
        rule.setPenaltyPerDay(ruleDetails.getPenaltyPerDay());
        rule.setMaxPenaltyPercentage(ruleDetails.getMaxPenaltyPercentage());
        rule.setActive(ruleDetails.getActive());
        rule.setIsDefaultRule(ruleDetails.getIsDefaultRule());
        
        return breachRuleRepository.save(rule);
    }
    
    @Override
    public List<BreachRule> getAllRules() {
        return breachRuleRepository.findAll();
    }
    
    @Override
    public BreachRule getRuleById(Long id) {
        return breachRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Rule not found with id: " + id));
    }
    
    @Override
    public BreachRule deactivateRule(Long id) {
        BreachRule rule = getRuleById(id);
        rule.setActive(false);
        rule.setIsDefaultRule(false);
        return breachRuleRepository.save(rule);
    }
    
    @Override
    public BreachRule getActiveDefaultOrFirst() {
        return breachRuleRepository.findFirstByActiveTrueOrderByIsDefaultRuleDesc()
            .orElseThrow(() -> new BadRequestException("No active breach rule found"));
    }
}