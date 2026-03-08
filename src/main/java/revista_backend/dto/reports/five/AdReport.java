package revista_backend.dto.reports.five;

import lombok.Value;

import java.time.LocalDate;

@Value
public class AdReport {
    String adTypeName;
    Integer cost;
    LocalDate date;
}
