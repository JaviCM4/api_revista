package revista_backend.dto.reports.one_nine;

import lombok.Value;

import java.time.LocalDate;

@Value
public class CommentReport {
    String name;
    String comment;
    LocalDate commentDate;
}
