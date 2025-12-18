public interface PenaltyCalculationService {

    PenaltyCalculation calculatePenalty(Long contractId);

    PenaltyCalculation getCalculationById(Long id);

    List<PenaltyCalculation> getCalculationsForContract(Long contractId);
}
