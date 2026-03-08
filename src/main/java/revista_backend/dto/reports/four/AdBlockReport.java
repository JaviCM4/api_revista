package revista_backend.dto.reports.four;

import lombok.Value;

import java.time.LocalDate;

@Value
public class AdBlockReport {
    String typeAdvertisementName;
    Integer payment;
    LocalDate startDate;
}
