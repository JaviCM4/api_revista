package revista_backend.dto.magazine.response;

import lombok.Value;

import java.util.List;

@Value
public class EditionFindResponse {
    Integer idMagazine;
    List<String> links;
}
