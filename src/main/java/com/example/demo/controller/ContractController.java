@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping
    public Contract createContract(@RequestBody Contract contract) {
        return contractService.createContract(contract);
    }

    @PutMapping("/{id}")
    public Contract updateContract(@PathVariable Long id,
                                   @RequestBody Contract contract) {
        return contractService.updateContract(id, contract);
    }

    @GetMapping("/{id}")
    public Contract getContract(@PathVariable Long id) {
        return contractService.getContractById(id);
    }

    @GetMapping
    public List<Contract> getAllContracts() {
        return contractService.getAllContracts();
    }

    @PutMapping("/{id}/update-status")
    public Contract updateStatus(@PathVariable Long id) {
        return contractService.updateContractStatus(id);
    }
}
