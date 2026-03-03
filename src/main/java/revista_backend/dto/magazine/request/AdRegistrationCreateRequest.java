package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class AdRegistrationCreateRequest {

    @NotNull
    @Min(1)
    Integer idAd;

    @NotBlank
    @Size(max = 250)
    String url;

    @NotNull
    @Min(0)
    Integer numberOfViews;

}
