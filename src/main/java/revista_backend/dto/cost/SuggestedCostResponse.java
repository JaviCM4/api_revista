package revista_backend.dto.cost;

import lombok.Value;
import revista_backend.models.cost.SuggestedCost;

@Value
public class SuggestedCostResponse {

    Integer idSuggestedCost;
    Integer cost;
    Integer days;

    public SuggestedCostResponse(SuggestedCost suggestedCost) {
        this.idSuggestedCost = suggestedCost.getId();
        this.cost = suggestedCost.getCost();
        this.days = suggestedCost.getDays();
    }
}
