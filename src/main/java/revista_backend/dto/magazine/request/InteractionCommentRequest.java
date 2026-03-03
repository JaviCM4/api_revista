package revista_backend.dto.magazine.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineInteraction;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Value
public class InteractionCommentRequest {

    @NotNull
    @Min(1)
    Integer idMagazine;

    @NotBlank
    @Size(max = 50)
    String comment;

    public MagazineInteraction createEntity(Magazine magazine, User user) {
        MagazineInteraction interaction = new MagazineInteraction();
        interaction.setMagazine(magazine);
        interaction.setUser(user);
        interaction.setComment(this.comment);
        interaction.setCommentDate(LocalDate.now());
        return interaction;
    }

}
