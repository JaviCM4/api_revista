package revista_backend.dto.reports.five;

import lombok.Value;

import java.time.LocalDate;

@Value
public class AdBlockReport {
    String editorName;
    Integer paymennt;
    LocalDate startDate;
}
