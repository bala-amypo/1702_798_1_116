@Service
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {

    private final ContractRepository contractRepo;
    private final DeliveryRecordRepository deliveryRepo;
    private final BreachRuleService breachRuleService;
    private final PenaltyCalculationRepository penaltyRepo;

    public PenaltyCalculationServiceImpl(
        ContractRepository contractRepo,
        DeliveryRecordRepository deliveryRepo,
        BreachRuleService breachRuleService,
        PenaltyCalculationRepository penaltyRepo) {

        this.contractRepo = contractRepo;
        this.deliveryRepo = deliveryRepo;
        this.breachRuleService = breachRuleService;
        this.penaltyRepo = penaltyRepo;
    }

    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {

        Contract contract = contractRepo.findById(contractId)
          .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        DeliveryRecord delivery = deliveryRepo
          .findFirstByContractIdOrderByDeliveryDateDesc(contractId)
          .orElseThrow(() ->
            new ResourceNotFoundException("No delivery record"));

        BreachRule rule = breachRuleService.getActiveDefaultOrFirst();

        long daysDelayed = ChronoUnit.DAYS.between(
            contract.getAgreedDeliveryDate().toInstant(),
            delivery.getDeliveryDate().toInstant());

        if (daysDelayed < 0) daysDelayed = 0;

        BigDecimal perDay = rule.getPenaltyPerDay()
            .multiply(BigDecimal.valueOf(daysDelayed));

        BigDecimal maxAllowed = contract.getBaseContractValue()
            .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage()))
            .divide(BigDecimal.valueOf(100));

        BigDecimal finalPenalty = perDay.min(maxAllowed);

        PenaltyCalculation calc = new PenaltyCalculation();
        calc.setContract(contract);
        calc.setDaysDelayed((int) daysDelayed);
        calc.setCalculatedPenalty(finalPenalty);
        calc.setAppliedRule(rule);
        calc.setCalculatedAt(new Timestamp(System.currentTimeMillis()));

        return penaltyRepo.save(calc);
    }
}
