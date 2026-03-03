package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.*;
import lombok.Value;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Value
public class MagazineCreateRequest {

    @NotBlank
    @Size(max = 50)
    String title;

    @NotBlank
    @Size(max = 250)
    String description;

    @NotNull
    boolean allowSubscription;

    @NotNull
    boolean allowComments;

    @NotNull
    boolean allowReactions;

    @PastOrPresent
    LocalDate createDate;

    public Magazine createEntity(User user) {
        Magazine magazine = new Magazine();
        magazine.setUser(user);
        magazine.setTitle(title);
        magazine.setDescription(description);
        magazine.setAllowSubscription(allowSubscription);
        magazine.setAllowComments(allowComments);
        magazine.setAllowReactions(allowReactions);
        magazine.setActiveMagazine(false);
        magazine.setDailyCost(0);
        magazine.setAdBlockCost(0);
        magazine.setCreationDate(createDate);
        return magazine;
    }

}
