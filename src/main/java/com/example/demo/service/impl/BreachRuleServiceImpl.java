@Service
public class BreachRuleServiceImpl implements BreachRuleService {

    private final BreachRuleRepository repo;

    public BreachRuleServiceImpl(BreachRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public BreachRule getActiveDefaultOrFirst() {
        BreachRule rule = repo.findFirstByActiveTrueOrderByIsDefaultRuleDesc();
        if (rule == null) {
            throw new ResourceNotFoundException("No active breach rule");
        }
        return rule;
    }
}
