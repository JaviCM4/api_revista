package revista_backend.dto.reports.three;

import lombok.Value;

import java.util.List;

@Value
public class ThreeReportResponse {
    Integer idMagazine;
    String nameMagazine;
    List<LikeReport> likes;
}
