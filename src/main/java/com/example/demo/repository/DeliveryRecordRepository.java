public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {
    DeliveryRecord findFirstByContractIdOrderByDeliveryDateDesc(Long contractId);
    List<DeliveryRecord> findByContractIdOrderByDeliveryDateAsc(Long contractId);
}
