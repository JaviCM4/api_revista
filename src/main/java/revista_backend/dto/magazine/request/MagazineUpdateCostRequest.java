package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class MagazineUpdateCostRequest {

    @NotNull
    @Min(1)
    Integer id;

    @NotNull
    @Min(1)
    Integer dailyCost;

    @NotNull
    @Min(1)
    Integer adBlockCost;

    public Integer getId() {
        return id;
    }

    public Integer getDailyCost() {
        return dailyCost;
    }

    public Integer getAdBlockCost() {
        return adBlockCost;
    }

}
