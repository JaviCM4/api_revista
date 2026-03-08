package revista_backend.dto.reports.two_eight;

import lombok.Value;

import java.util.List;

@Value
public class TwoEigthReportResponse {
    Integer idMagazine;
    String nameMagazine;
    List<ReportSubscription> subscriptions;
}
