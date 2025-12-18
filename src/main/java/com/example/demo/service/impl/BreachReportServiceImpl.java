@Service
public class BreachReportServiceImpl
        implements BreachReportService {

    @Autowired
    private BreachReportRepository reportRepository;

    @Autowired
    private PenaltyCalculationService penaltyService;

    @Override
    public BreachReport generateReport(Long contractId) {

        PenaltyCalculation calculation =
                penaltyService.calculatePenalty(contractId);

        BreachReport report = new BreachReport();
        report.setContractId(contractId);
        report.setPenaltyAmount(calculation.getPenaltyAmount());
        report.setSummary(
                "Contract delayed by " +
                calculation.getDaysDelayed() + " days"
        );
        report.setReportDate(LocalDate.now());

        return reportRepository.save(report);
    }

    @Override
    public BreachReport getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    @Override
    public List<BreachReport> getReportsForContract(Long contractId) {
        return reportRepository.findByContractId(contractId);
    }

    @Override
    public List<BreachReport> getAllReports() {
        return reportRepository.findAll();
    }
}
