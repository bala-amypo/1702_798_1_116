@RestController
@RequestMapping("/api/reports")
public class BreachReportController {

    @Autowired
    private BreachReportService reportService;

    @PostMapping("/generate/{contractId}")
    public BreachReport generateReport(
            @PathVariable Long contractId) {
        return reportService.generateReport(contractId);
    }

    @GetMapping("/{id}")
    public BreachReport getReport(@PathVariable Long id) {
        return reportService.getReportById(id);
    }

    @GetMapping("/contract/{contractId}")
    public List<BreachReport> getReportsForContract(
            @PathVariable Long contractId) {
        return reportService.getReportsForContract(contractId);
    }

    @GetMapping
    public List<BreachReport> getAllReports() {
        return reportService.getAllReports();
    }
}
