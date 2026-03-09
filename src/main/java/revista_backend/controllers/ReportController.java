package revista_backend.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import revista_backend.dto.reports.five.FiveReportResponse;
import revista_backend.dto.reports.four.FourthReportResponse;
import revista_backend.dto.reports.one_nine.OneNineReportResponse;
import revista_backend.dto.reports.seven.SeventhReportResponse;
import revista_backend.dto.reports.six.SixReportResponse;
import revista_backend.dto.reports.three.ThreeReportResponse;
import revista_backend.dto.reports.two_eight.TwoEigthReportResponse;
import revista_backend.exceptions.ValidationException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.reports.ReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    private final ReportService reportService;
    private final SecurityUtils securityUtils;

    @Autowired
    public ReportController(ReportService reportService, SecurityUtils securityUtils) {
        this.reportService = reportService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("/comments")
    @RolesAllowed("EDITOR")
    public ResponseEntity<List<OneNineReportResponse>> getCommentsReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) throws ValidationException {
        validateDateRange(startDate, endDate);
        int userId = securityUtils.getUserIdFromRequest(request);
        return ResponseEntity.ok(reportService.firstEditorAdminReport(startDate, endDate, userId));
    }

    @GetMapping("/subscriptions")
    @RolesAllowed("EDITOR")
    public ResponseEntity<List<TwoEigthReportResponse>> getSubscriptionsReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) throws ValidationException {
        validateDateRange(startDate, endDate);
        int userId = securityUtils.getUserIdFromRequest(request);
        return ResponseEntity.ok(reportService.secondEditorAdminReport(startDate, endDate, userId));
    }

    @GetMapping("/likes")
    @RolesAllowed("EDITOR")
    public ResponseEntity<List<ThreeReportResponse>> getLikesReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) throws ValidationException {
        validateDateRange(startDate, endDate);
        int userId = securityUtils.getUserIdFromRequest(request);
        return ResponseEntity.ok(reportService.thirdEditorReport(startDate, endDate, userId));
    }

    @GetMapping("/payments")
    @RolesAllowed("EDITOR")
    public ResponseEntity<List<FourthReportResponse>> getPaymentsReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            HttpServletRequest request) throws ValidationException {
        validateDateRange(startDate, endDate);
        int userId = securityUtils.getUserIdFromRequest(request);
        return ResponseEntity.ok(reportService.fourthEditorReport(startDate, endDate, userId));
    }

    @GetMapping("/financial-summary")
    @RolesAllowed("ADMIN")
    public ResponseEntity<FiveReportResponse> getFinancialSummaryReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws ValidationException {
        validateDateRange(startDate, endDate);
        return ResponseEntity.ok(reportService.fiveAdminReport(startDate, endDate));
    }

    @GetMapping("/advertisements")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<SixReportResponse>> getAdvertisementsReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws ValidationException {
        validateDateRange(startDate, endDate);
        return ResponseEntity.ok(reportService.sixAdminReport(startDate, endDate));
    }

    @GetMapping("/advertisers")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<SeventhReportResponse>> getAdvertisersReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws ValidationException {
        validateDateRange(startDate, endDate);
        return ResponseEntity.ok(reportService.seventhReport(startDate, endDate));
    }

    @GetMapping("/subscriptions/admin")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<TwoEigthReportResponse>> getSubscriptionsAdminReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws ValidationException {
        validateDateRange(startDate, endDate);
        return ResponseEntity.ok(reportService.eighthReport(startDate, endDate));
    }

    @GetMapping("/comments/admin")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<OneNineReportResponse>> getCommentsAdminReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
            throws ValidationException {
        validateDateRange(startDate, endDate);
        return ResponseEntity.ok(reportService.nineReport(startDate, endDate));
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) throws ValidationException {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ValidationException("startDate must be before or equal to endDate");
        }
    }
}