@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Override
    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public Contract updateContract(Long id, Contract contract) {
        Contract existing = contractRepository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setName(contract.getName());
        existing.setDueDate(contract.getDueDate());
        existing.setStatus(contract.getStatus());

        return contractRepository.save(existing);
    }

    @Override
    public Contract getContractById(Long id) {
        return contractRepository.findById(id).orElse(null);
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract updateContractStatus(Long contractId) {
        Contract contract = contractRepository.findById(contractId).orElse(null);
        if (contract == null) return null;

        contract.setStatus("COMPLETED");
        return contractRepository.save(contract);
    }
}
