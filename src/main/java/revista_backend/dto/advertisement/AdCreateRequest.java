package revista_backend.dto.advertisement;

import jakarta.validation.constraints.*;
import lombok.Value;
import revista_backend.models.advertisement.Advertisement;
import revista_backend.models.status.AdStatus;
import revista_backend.models.types.AdType;
import revista_backend.models.user.User;

import java.time.LocalDate;
import java.util.List;

@Value
public class AdCreateRequest {

    @NotNull
    @Min(1)
    Integer idTypeAd;

    @NotEmpty
    List<String> detail;

    @NotNull
    @Min(1)
    Integer totalCost;

    @Future
    LocalDate expirationDate;

    public Advertisement createEntity(User user, AdType adType, AdStatus adStatus) {
        Advertisement ad = new Advertisement();
        ad.setUser(user);
        ad.setAdType(adType);
        ad.setAdStatus(adStatus);
        ad.setTotalCost(totalCost);
        ad.setCreationDate(LocalDate.now());
        ad.setExpirationDate(expirationDate);
        return ad;
    }

}
