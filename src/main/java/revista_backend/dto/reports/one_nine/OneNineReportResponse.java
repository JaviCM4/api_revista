package revista_backend.dto.reports.one_nine;

import lombok.Value;

import java.util.List;

@Value
public class OneNineReportResponse {
    Integer idMagazine;
    String nameMagazine;
    List<CommentReport> comments;
}
