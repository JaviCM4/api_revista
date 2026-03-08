package revista_backend.dto.reports.five;

import lombok.Value;
import revista_backend.dto.reports.four.PaymentReport;

import java.util.List;

@Value
public class MagazineReport {
    String nameMagazine;
    List<PaymentReport> payments;
}
