package revista_backend.dto.reports.six;

import lombok.Value;
import revista_backend.models.advertisement.Advertisement;

import java.time.LocalDate;
import java.util.List;

@Value
public class SixReportResponse {

    Integer idAd;
    String nameTypeAd;
    Integer cost;
    LocalDate dateAd;
    List<String> links;

    public SixReportResponse(Advertisement advertisement, List<String> links) {
        this.idAd = advertisement.getId();
        this.nameTypeAd = advertisement.getAdType().getName();
        this.cost = advertisement.getTotalCost();
        this.dateAd = advertisement.getCreationDate();
        this.links = links;
    }
}
