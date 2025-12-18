@Service
public class DeliveryRecordServiceImpl
        implements DeliveryRecordService {

    @Autowired
    private DeliveryRecordRepository recordRepository;

    @Override
    public DeliveryRecord createDeliveryRecord(DeliveryRecord record) {
        return recordRepository.save(record);
    }

    @Override
    public List<DeliveryRecord> getDeliveryRecordsForContract(Long contractId) {
        return recordRepository.findByContractId(contractId);
    }

    @Override
    public DeliveryRecord getLatestDeliveryRecord(Long contractId) {

        List<DeliveryRecord> records =
                recordRepository.findByContractIdOrderByDeliveredOnDesc(contractId);

        if (records.isEmpty()) {
            throw new RuntimeException("No delivery records found");
        }

        return records.get(0);
    }

    @Override
    public DeliveryRecord getRecordById(Long id) {
        return recordRepository.findById(id).orElse(null);
    }
}
