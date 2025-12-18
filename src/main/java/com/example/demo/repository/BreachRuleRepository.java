package com.example.demo.repository;

import com.example.demo.entity.BreachRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BreachRuleRepository extends JpaRepository<BreachRule, Long> {

    Optional<BreachRule> findByRuleName(String ruleName);

    Optional<BreachRule> findByIsDefaultRuleTrueAndActiveTrue();

    // Used to ensure only ONE active default rule
    @Modifying
    @Query("UPDATE BreachRule b SET b.isDefaultRule = false WHERE b.isDefaultRule = true")
    void unsetOtherDefaultRules();
}
