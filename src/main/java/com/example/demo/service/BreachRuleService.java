package com.example.demo.service;

import com.example.demo.entity.BreachRule;
import java.util.List;

public interface BreachRuleService {
    BreachRule createRule(BreachRule rule);
    BreachRule getRuleById(Long id);
    BreachRule updateRule(Long id, BreachRule rule);
    void deleteRule(Long id);
    List<BreachRule> getAllRules();
    BreachRule getActiveDefaultOrFirst();
    void deactivateRule(Long id);
}