package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class MagazineUpdatePermissionsRequest {

    @NotNull
    @Min(1)
    Integer id;

    @NotNull
    boolean allowSubscription;

    @NotNull
    boolean allowComments;

    @NotNull
    boolean allowReactions;
}
