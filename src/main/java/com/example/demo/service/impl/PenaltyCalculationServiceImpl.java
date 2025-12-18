@Service
public class PenaltyCalculationServiceImpl
        implements PenaltyCalculationService {

    @Autowired
    private PenaltyCalculationRepository repository;

    @Override
    public PenaltyCalculation calculatePenalty(Long contractId) {

        // Sample data (normally fetched from DB)
        LocalDate dueDate = LocalDate.now().minusDays(5);
        LocalDate deliveryDate = LocalDate.now();
        double penaltyPerDay = 100.0;

        int daysDelayed =
                (int) ChronoUnit.DAYS.between(dueDate, deliveryDate);

        double penaltyAmount = daysDelayed * penaltyPerDay;

        PenaltyCalculation calculation = new PenaltyCalculation();
        calculation.setContractId(contractId);
        calculation.setDaysDelayed(daysDelayed);
        calculation.setPenaltyAmount(penaltyAmount);
        calculation.setCalculatedOn(LocalDate.now());

        return repository.save(calculation);
    }

    @Override
    public PenaltyCalculation getCalculationById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<PenaltyCalculation> getCalculationsForContract(Long contractId) {
        return repository.findByContractId(contractId);
    }
}
