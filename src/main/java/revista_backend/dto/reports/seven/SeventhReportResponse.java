package revista_backend.dto.reports.seven;

import lombok.Value;

import java.util.List;

@Value
public class SeventhReportResponse {

    String advertiserName;
    List<AdCostReport> advertisements;

    public SeventhReportResponse(String advertiserName, List<AdCostReport> advertisements) {
        this.advertiserName = advertiserName;
        this.advertisements = advertisements;
    }
}
