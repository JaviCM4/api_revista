package revista_backend.dto.reports.seven;

import lombok.Value;

import java.time.LocalDate;

@Value
public class AdCostReport {

    Integer id;
    String adTypeName;
    Integer totalCost;
    LocalDate creationDate;

    public AdCostReport(Integer id, String adTypeName, int totalCost, LocalDate creationDate) {
        this.id = id;
        this.adTypeName = adTypeName;
        this.totalCost = totalCost;
        this.creationDate = creationDate;
    }
}
