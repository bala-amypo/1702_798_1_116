package com.example.demo.service;

import com.example.demo.model.BreachRule;
import java.util.List;

public interface BreachRuleService {
    BreachRule createRule(BreachRule rule);
    BreachRule updateRule(Long id, BreachRule rule);
    List<BreachRule> getAllRules();
    BreachRule getRuleById(Long id);
    BreachRule deactivateRule(Long id);
    BreachRule getActiveDefaultOrFirst();
}