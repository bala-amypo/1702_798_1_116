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
