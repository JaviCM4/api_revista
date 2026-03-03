package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Value;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineSubscription;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Value
public class SubscriptionCreateRequest {

    @NotNull
    @Min(1)
    Integer idMagazine;

    @PastOrPresent
    LocalDate createDate;

    public MagazineSubscription create(Magazine magazine, User User) {
        MagazineSubscription newSubscription = new MagazineSubscription();
        newSubscription.setMagazine(magazine);
        newSubscription.setUser(User);
        newSubscription.setSubscriptionDate(createDate);
        return newSubscription;
    }
}
