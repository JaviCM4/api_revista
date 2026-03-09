package revista_backend.services.cost;

import revista_backend.dto.cost.SuggestedCostCreateResquest;
import revista_backend.dto.cost.SuggestedCostResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;

import java.util.List;

public interface SuggestedCostService {

    void create(SuggestedCostCreateResquest dto)
            throws ResourceNotFoundException, ConflictException;

    List<SuggestedCostResponse> findAll();

    void delete(Integer idSuggestedCost)
            throws ResourceNotFoundException;
}
