package revista_backend.dto.advertisement;

import lombok.Value;

import java.util.List;

@Value
public class AdFindResponse {

    Integer idAdvertisement;
    Integer idAdType;
    List<String> links;
}
