@RestController
@RequestMapping("/api/penalties")
public class PenaltyCalculationController {

    @Autowired
    private PenaltyCalculationService penaltyService;

    @PostMapping("/calculate/{contractId}")
    public PenaltyCalculation calculatePenalty(
            @PathVariable Long contractId) {
        return penaltyService.calculatePenalty(contractId);
    }

    @GetMapping("/{id}")
    public PenaltyCalculation getCalculation(@PathVariable Long id) {
        return penaltyService.getCalculationById(id);
    }

    @GetMapping("/contract/{contractId}")
    public List<PenaltyCalculation> getCalculationsForContract(
            @PathVariable Long contractId) {
        return penaltyService.getCalculationsForContract(contractId);
    }
}
