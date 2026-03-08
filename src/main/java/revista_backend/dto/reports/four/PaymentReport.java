package revista_backend.dto.reports.four;

import lombok.Value;

import java.time.LocalDate;

@Value
public class PaymentReport {
    Integer payment;
    LocalDate paymentDate;
}
