package com.example.demo.repository;

import com.example.demo.entity.BreachRule;
import java.util.List;
import java.util.Optional;

public interface BreachRuleRepository {
    BreachRule save(BreachRule breachRule);
    Optional<BreachRule> findById(Long id);
    List<BreachRule> findAll();
    Optional<BreachRule> findFirstByActiveTrueOrderByIsDefaultRuleDesc();
}