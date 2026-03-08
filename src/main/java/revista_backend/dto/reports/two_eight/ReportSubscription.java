package revista_backend.dto.reports.two_eight;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ReportSubscription {
    String name;
    LocalDate subscriptionDate;
}
