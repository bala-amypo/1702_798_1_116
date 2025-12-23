@Service
public class BreachRuleServiceImpl implements BreachRuleService {

    private final BreachRuleRepository repository;

    public BreachRuleServiceImpl(BreachRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public BreachRule getActiveDefaultOrFirst() {
        BreachRule rule = repository.findFirstByActiveTrueOrderByIsDefaultRuleDesc();
        if (rule == null) {
            throw new ResourceNotFoundException("No active breach rule");
        }
        return rule;
    }
}
