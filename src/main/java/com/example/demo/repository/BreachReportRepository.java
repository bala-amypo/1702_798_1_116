public interface BreachReportRepository extends JpaRepository<BreachReport, Long> {
    List<BreachReport> findByContractId(Long contractId);
}
