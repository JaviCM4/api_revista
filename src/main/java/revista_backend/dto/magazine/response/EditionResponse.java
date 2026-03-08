package revista_backend.dto.magazine.response;

import lombok.Value;
import revista_backend.models.magazine.Edition;

@Value
public class EditionResponse {
    Integer idEdition;
    String resources;

    public EditionResponse(Edition edition) {
        this.idEdition = edition.getId();
        this.resources = edition.getResource();
    }
}
