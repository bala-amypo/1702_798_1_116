package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.service.BreachRuleService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BreachRuleServiceImpl implements BreachRuleService {
    
    private final BreachRuleRepository breachRuleRepository;
    
    public BreachRuleServiceImpl(BreachRuleRepository breachRuleRepository) {
        this.breachRuleRepository = breachRuleRepository;
    }
    
    @Override
    public BreachRule createRule(BreachRule rule) {
        if (rule.getPenaltyPerDay() == null || rule.getPenaltyPerDay().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Penalty per day must be positive");
        }
        
        if (rule.getMaxPenaltyPercentage() == null || 
            rule.getMaxPenaltyPercentage() <= 0 || 
            rule.getMaxPenaltyPercentage() > 100) {
            throw new IllegalArgumentException("Max penalty percentage must be between 0 and 100");
        }
        
        if (rule.getActive() == null) {
            rule.setActive(true);
        }
        
        if (rule.getIsDefaultRule() == null) {
            rule.setIsDefaultRule(false);
        }
        
        return breachRuleRepository.save(rule);
    }
    
    @Override
    @Transactional(readOnly = true)
    public BreachRule getRuleById(Long id) {
        return breachRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rule not found with id: " + id));
    }
    
    @Override
    public BreachRule updateRule(Long id, BreachRule rule) {
        BreachRule existing = getRuleById(id);
        existing.setRuleName(rule.getRuleName());
        existing.setPenaltyPerDay(rule.getPenaltyPerDay());
        existing.setMaxPenaltyPercentage(rule.getMaxPenaltyPercentage());
        existing.setActive(rule.getActive());
        existing.setIsDefaultRule(rule.getIsDefaultRule());
        return breachRuleRepository.save(existing);
    }
    
    @Override
    public void deleteRule(Long id) {
        breachRuleRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<BreachRule> getAllRules() {
        return breachRuleRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public BreachRule getActiveDefaultOrFirst() {
        return breachRuleRepository.findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow(() -> new ResourceNotFoundException("No active breach rule found"));
    }
    
    @Override
    public void deactivateRule(Long id) {
        BreachRule rule = getRuleById(id);
        rule.setActive(false);
        breachRuleRepository.save(rule);
    }
}