package com.example.demo.service.impl;

import com.example.demo.entity.BreachRule;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BreachRuleRepository;
import com.example.demo.service.BreachRuleService;

import org.springframework.stereotype.Service;

@Service
public class BreachRuleServiceImpl implements BreachRuleService {

    private final BreachRuleRepository repo;

    public BreachRuleServiceImpl(BreachRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public BreachRule getActiveDefaultOrFirst() {
        return repo.findFirstByActiveTrueOrderByIsDefaultRuleDesc()
                .orElseThrow(() ->
                        new ResourceNotFoundException("No active breach rule"));
    }
}
