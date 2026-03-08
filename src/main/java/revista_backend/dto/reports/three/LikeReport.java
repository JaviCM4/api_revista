package revista_backend.dto.reports.three;

import lombok.Value;

import java.time.LocalDate;

@Value
public class LikeReport {
    String name;
    LocalDate likeDate;
}
