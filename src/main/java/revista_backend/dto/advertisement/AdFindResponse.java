package revista_backend.dto.advertisement;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class AdFindResponse {

    Integer idAdvertisement;
    Integer idAdType;
    LocalDate expirationDate;
    List<String> links;
}
