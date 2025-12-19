public interface VendorTierRepository extends JpaRepository<VendorTier, Long> {
    List<VendorTier> findByActiveTrueOrderByMinScoreThresholdDesc();
    boolean existsByTierName(String name);
}
