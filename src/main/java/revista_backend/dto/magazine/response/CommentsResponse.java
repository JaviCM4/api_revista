package revista_backend.dto.magazine.response;

import lombok.Value;
import revista_backend.models.magazine.MagazineInteraction;

@Value
public class CommentsResponse {

    String names;
    String comments;

    public CommentsResponse(MagazineInteraction magazineInteraction) {
        this.names = magazineInteraction.getUser().getNames() + " " + magazineInteraction.getUser().getLastNames();
        this.comments = magazineInteraction.getComment();
    }
}
