package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class InteractionLikeRequest {

    @NotNull
    @Min(1)
    Integer idMagazine;

}
