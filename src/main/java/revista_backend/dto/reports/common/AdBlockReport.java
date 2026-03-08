package revista_backend.dto.reports.common;

import lombok.Value;

import java.time.LocalDate;

@Value
public class AdBlockReport {
    String typeAdvertisementName;
    Integer payment;
    LocalDate startDate;
}
