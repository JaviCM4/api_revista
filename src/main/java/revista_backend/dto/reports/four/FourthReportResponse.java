package revista_backend.dto.reports.four;

import lombok.Value;
import revista_backend.dto.reports.common.AdBlockReport;

import java.util.List;

@Value
public class FourthReportResponse {
    Integer idMagazine;
    String nameMagazine;
    List<PaymentReport> paymentReports;
    List<AdBlockReport> adBlocks;
}
