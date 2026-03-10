package revista_backend.services.cost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revista_backend.dto.cost.SuggestedCostCreateResquest;
import revista_backend.dto.cost.SuggestedCostResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.cost.SuggestedCost;
import revista_backend.repositories.cost.SuggestedCostRepository;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class SuggestedCostServiceImplementation implements SuggestedCostService {

    private final SuggestedCostRepository suggestedCostRepository;

    @Autowired
    public SuggestedCostServiceImplementation(SuggestedCostRepository suggestedCostRepository) {
        this.suggestedCostRepository = suggestedCostRepository;
    }

    @Override
    public void create(SuggestedCostCreateResquest dto)
            throws ResourceNotFoundException, ConflictException {

        if (suggestedCostRepository.existsByDays(dto.getDays())) {
            throw new ConflictException("That duration are already registered");
        }

        SuggestedCost suggestedCost = dto.createEntity();
        suggestedCostRepository.save(suggestedCost);
    }

    @Override
    public List<SuggestedCostResponse> findAll() {
        return suggestedCostRepository.findAll()
                .stream()
                .map(SuggestedCostResponse::new)
                .toList();
    }

    @Override
    public void delete(Integer idSuggestedCost) throws ResourceNotFoundException {
        SuggestedCost suggestedCost = suggestedCostRepository.findById(idSuggestedCost)
                .orElseThrow(() -> new ResourceNotFoundException("Type User not found"));

        suggestedCostRepository.delete(suggestedCost);
    }
}