package revista_backend.services.reports;

import revista_backend.dto.reports.five.FiveReportResponse;
import revista_backend.dto.reports.four.FourthReportResponse;
import revista_backend.dto.reports.one_nine.OneNineReportResponse;
import revista_backend.dto.reports.seven.SeventhReportResponse;
import revista_backend.dto.reports.six.SixReportResponse;
import revista_backend.dto.reports.three.ThreeReportResponse;
import revista_backend.dto.reports.two_eight.TwoEigthReportResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    List<OneNineReportResponse> firstEditorAdminReport(LocalDate start, LocalDate end, Integer idUser);

    List<TwoEigthReportResponse> secondEditorAdminReport(LocalDate start, LocalDate end, Integer idUser);

    List<ThreeReportResponse> thirdEditorReport(LocalDate start, LocalDate end, Integer idUser);

    List<FourthReportResponse> fourthEditorReport(LocalDate start, LocalDate end, Integer idUser);

    FiveReportResponse fiveAdminReport(LocalDate start, LocalDate end);

    List<SixReportResponse> sixAdminReport(LocalDate star, LocalDate end);

    List<SeventhReportResponse> seventhReport(LocalDate star, LocalDate end);

    List<TwoEigthReportResponse> eighthReport(LocalDate start, LocalDate end);

    List<OneNineReportResponse> nineReport(LocalDate start, LocalDate end);
}