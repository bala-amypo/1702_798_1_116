@Service
public class PenaltyCalculationServiceImpl implements PenaltyCalculationService {

    private final ContractRepository contractRepo;
    private final DeliveryRecordRepository deliveryRepo;
    private final BreachRuleService breachRuleService;
    private final PenaltyCalculationRepository calcRepo;

    public PenaltyCalculationServiceImpl(
            ContractRepository c,
            DeliveryRecordRepository d,
            BreachRuleService b,
            PenaltyCalculationRepository p) {
        this.contractRepo = c;
        this.deliveryRepo = d;
        this.breachRuleService = b;
        this.calcRepo = p;
    }

    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {

        Contract contract = contractRepo.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found"));

        DeliveryRecord latest = deliveryRepo
                .findFirstByContractIdOrderByDeliveryDateDesc(contractId);

        if (latest == null) {
            throw new BadRequestException("No delivery record");
        }

        BreachRule rule = breachRuleService.getActiveDefaultOrFirst();

        long delay = Math.max(0,
                (latest.getDeliveryDate().getTime()
                        - contract.getAgreedDeliveryDate().getTime())
                        / (1000 * 60 * 60 * 24));

        BigDecimal penalty = rule.getPenaltyPerDay()
                .multiply(BigDecimal.valueOf(delay));

        BigDecimal maxCap = contract.getBaseContractValue()
                .multiply(BigDecimal.valueOf(rule.getMaxPenaltyPercentage() / 100));

        BigDecimal finalPenalty = penalty.min(maxCap);

        PenaltyCalculation calc = new PenaltyCalculation();
        calc.setContract(contract);
        calc.setDaysDelayed((int) delay);
        calc.setCalculatedPenalty(finalPenalty);
        calc.setAppliedRule(rule);
        calc.setCalculatedAt(new Date());

        return calcRepo.save(calc);
    }
}
