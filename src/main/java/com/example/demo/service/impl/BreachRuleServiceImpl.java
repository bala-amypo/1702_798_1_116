@Service
public class BreachRuleServiceImpl implements BreachRuleService {

    @Autowired
    private BreachRuleRepository ruleRepository;

    @Override
    public BreachRule createRule(BreachRule rule) {
        rule.setActive(true);
        return ruleRepository.save(rule);
    }

    @Override
    public BreachRule updateRule(Long id, BreachRule rule) {

        BreachRule existing = ruleRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setPenaltyPerDay(rule.getPenaltyPerDay());
        existing.setDescription(rule.getDescription());

        return ruleRepository.save(existing);
    }

    @Override
    public List<BreachRule> getAllRules() {
        return ruleRepository.findAll();
    }

    @Override
    public BreachRule getRuleById(Long id) {
        return ruleRepository.findById(id).orElse(null);
    }

    @Override
    public void deactivateRule(Long id) {
        BreachRule rule = ruleRepository.findById(id).orElse(null);
        if (rule != null) {
            rule.setActive(false);
            ruleRepository.save(rule);
        }
    }

    @Override
    public BreachRule getActiveDefaultOrFirst() {

        BreachRule activeRule = ruleRepository.findByActiveTrue();
        if (activeRule != null) {
            return activeRule;
        }

        List<BreachRule> rules = ruleRepository.findAll();
        return rules.isEmpty() ? null : rules.get(0);
    }
}
