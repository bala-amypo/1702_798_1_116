public interface PenaltyCalculationRepository extends JpaRepository<PenaltyCalculation, Long> {
    Optional<PenaltyCalculation> findTopByContractIdOrderByCalculatedAtDesc(Long contractId);
    List<PenaltyCalculation> findByContractId(Long contractId);
}
