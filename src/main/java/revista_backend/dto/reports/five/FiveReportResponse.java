package revista_backend.dto.reports.five;

import lombok.Value;
import revista_backend.dto.reports.common.AdBlockReport;

import java.util.List;

@Value
public class FiveReportResponse {
    List<MagazineReport> magazines;
    List<AdReport> ads;
    List<AdBlockReport> adBlocks;
}
