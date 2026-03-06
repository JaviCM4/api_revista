package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.InteractionLike;
import revista_backend.models.user.User;

@Value
public class InteractionCommentRequest {

    @NotNull
    @Min(1)
    Integer idMagazine;

    @NotBlank
    @Size(max = 50)
    String comment;

    public InteractionLike createEntity(Magazine magazine, User user) {
        InteractionLike interaction = new InteractionLike();
        interaction.setMagazine(magazine);
        interaction.setUser(user);
        return interaction;
    }

}
