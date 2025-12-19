public interface PenaltyCalculationRepository extends JpaRepository<PenaltyCalculation, Long> {
    PenaltyCalculation findTopByContractIdOrderByCalculatedAtDesc(Long contractId);
    List<PenaltyCalculation> findByContractId(Long contractId);
}
