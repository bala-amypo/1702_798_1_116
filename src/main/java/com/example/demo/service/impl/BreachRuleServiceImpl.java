package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.service.BreachRuleService;

import java.util.List;

public class BreachRuleServiceImpl implements BreachRuleService {

    private final BreachRuleRepository repository;

    public BreachRuleServiceImpl(BreachRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public BreachRule getActiveDefaultOrFirst() {
        List<BreachRule> rules = repository.findAll();
        return rules.stream()
                .filter(BreachRule::isActive)
                .findFirst()
                .orElse(rules.isEmpty() ? null : rules.get(0));
    }
}
