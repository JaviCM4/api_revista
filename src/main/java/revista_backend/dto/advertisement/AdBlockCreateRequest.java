package revista_backend.dto.advertisement;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import revista_backend.models.advertisement.AdBlock;
import revista_backend.models.advertisement.Advertisement;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.status.AdLockStatus;

import java.time.LocalDate;

@Value
public class AdBlockCreateRequest {

    @NotNull
    @Min(1)
    Integer idMagazine;

    @NotNull
    @Min(1)
    Integer inAdvertisement;

    @NotNull
    @Min(1)
    Integer cost;

    @Future
    LocalDate expirationDate;

    public AdBlock createEntity(Magazine magazine, Advertisement advertisement, AdLockStatus adLockStatus) {
        AdBlock adBlock = new AdBlock();
        adBlock.setMagazine(magazine);
        adBlock.setAdvertisement(advertisement);
        adBlock.setAdLockStatus(adLockStatus);
        adBlock.setPayment(cost);
        adBlock.setStartDate(LocalDate.now());
        adBlock.setEndDate(expirationDate);
        return adBlock;
    }
}