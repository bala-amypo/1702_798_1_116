public interface BreachRuleRepository extends JpaRepository<BreachRule, Long> {
    BreachRule findFirstByActiveTrueOrderByIsDefaultRuleDesc();
}
