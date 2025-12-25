package com.example.demo.service;

import com.example.demo.entity.BreachRule;
import java.util.List;

public interface BreachRuleService {
    BreachRule createRule(BreachRule breachRule);
    void deactivateRule(Long id);
    BreachRule getActiveDefaultOrFirst();
    List<BreachRule> getAllRules();
}