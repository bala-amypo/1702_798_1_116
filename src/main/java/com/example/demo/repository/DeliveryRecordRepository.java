public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {
    Optional<DeliveryRecord> findFirstByContractIdOrderByDeliveryDateDesc(Long contractId);
    List<DeliveryRecord> findByContractIdOrderByDeliveryDateAsc(Long contractId);
}
