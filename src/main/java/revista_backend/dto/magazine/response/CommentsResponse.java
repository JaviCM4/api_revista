package revista_backend.dto.magazine.response;

import lombok.Value;
import revista_backend.models.magazine.InteractionComment;
import revista_backend.models.magazine.InteractionLike;

@Value
public class CommentsResponse {

    Integer idComment;
    String names;
    String comments;

    public CommentsResponse(InteractionComment interactionComment) {
        this.idComment = interactionComment.getId();
        this.names = interactionComment.getUser().getNames() + " " + interactionComment.getUser().getLastNames();
        this.comments = interactionComment.getComment();
    }
}
