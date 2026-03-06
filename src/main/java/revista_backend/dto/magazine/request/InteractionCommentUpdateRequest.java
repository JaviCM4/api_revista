package revista_backend.dto.magazine.request;

import lombok.Value;

@Value
public class InteractionCommentUpdateRequest {
    Integer idInteraction;
    String comment;
}
