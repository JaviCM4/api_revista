package revista_backend.dto.cost;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import revista_backend.models.cost.SuggestedCost;
import revista_backend.models.types.CostType;

@Value
public class SuggestedCostCreateResquest {

    @NotNull
    @Min(1)
    Integer cost;

    @NotNull
    @Min(1)
    Integer days;

    public SuggestedCost createEntity() {
        SuggestedCost suggestedCost = new SuggestedCost();
        suggestedCost.setCost(cost);
        suggestedCost.setDays(days);
        return suggestedCost;
    }
}
