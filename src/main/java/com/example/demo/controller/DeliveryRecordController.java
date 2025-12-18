@RestController
@RequestMapping("/api/delivery-records")
public class DeliveryRecordController {

    @Autowired
    private DeliveryRecordService deliveryService;

    @PostMapping
    public DeliveryRecord createRecord(@RequestBody DeliveryRecord record) {
        return deliveryService.createDeliveryRecord(record);
    }

    @GetMapping("/{id}")
    public DeliveryRecord getRecord(@PathVariable Long id) {
        return deliveryService.getRecordById(id);
    }

    @GetMapping("/contract/{contractId}")
    public List<DeliveryRecord> getRecordsForContract(
            @PathVariable Long contractId) {
        return deliveryService.getDeliveryRecordsForContract(contractId);
    }

    @GetMapping("/contract/{contractId}/latest")
    public DeliveryRecord getLatestRecord(
            @PathVariable Long contractId) {
        return deliveryService.getLatestDeliveryRecord(contractId);
    }
}
