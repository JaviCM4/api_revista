package revista_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import revista_backend.CommonMvcTest;
import revista_backend.dto.reports.five.FiveReportResponse;
import revista_backend.exceptionhandler.ControllerExceptionHandler;
import revista_backend.security.SecurityUtils;
import revista_backend.services.reports.ReportService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReportController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {ReportController.class, ControllerExceptionHandler.class})
public class ReportControllerTest extends CommonMvcTest {

    private static final int USER_ID = 7;

    @MockBean
    private ReportService reportService;

    @MockBean
    private SecurityUtils securityUtils;

    @Test
    void testGetCommentsReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 1, 31);
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);
        when(reportService.firstEditorAdminReport(start, end, USER_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/comments")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).firstEditorAdminReport(start, end, USER_ID);
    }

    @Test
    void testGetSubscriptionsReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 2, 1);
        LocalDate end = LocalDate.of(2026, 2, 28);
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);
        when(reportService.secondEditorAdminReport(start, end, USER_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/subscriptions")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).secondEditorAdminReport(start, end, USER_ID);
    }

    @Test
    void testGetLikesReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 3, 1);
        LocalDate end = LocalDate.of(2026, 3, 8);
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);
        when(reportService.thirdEditorReport(start, end, USER_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/likes")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).thirdEditorReport(start, end, USER_ID);
    }

    @Test
    void testGetPaymentsReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 4, 1);
        LocalDate end = LocalDate.of(2026, 4, 15);
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);
        when(reportService.fourthEditorReport(start, end, USER_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/payments")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).fourthEditorReport(start, end, USER_ID);
    }

    @Test
    void testGetFinancialSummaryReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 12, 31);
        FiveReportResponse response = new FiveReportResponse(List.of(), List.of(), List.of());
        when(reportService.fiveAdminReport(start, end)).thenReturn(response);

        mockMvc.perform(get("/v1/reports/financial-summary")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

        verify(reportService).fiveAdminReport(start, end);
    }

    @Test
    void testGetAdvertisementsReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 5, 1);
        LocalDate end = LocalDate.of(2026, 5, 31);
        when(reportService.sixAdminReport(start, end)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/advertisements")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).sixAdminReport(start, end);
    }

    @Test
    void testGetAdvertisersReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 6, 1);
        LocalDate end = LocalDate.of(2026, 6, 30);
        when(reportService.seventhReport(start, end)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/advertisers")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).seventhReport(start, end);
    }

    @Test
    void testGetSubscriptionsAdminReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 7, 1);
        LocalDate end = LocalDate.of(2026, 7, 31);
        when(reportService.eighthReport(start, end)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/subscriptions/admin")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).eighthReport(start, end);
    }

    @Test
    void testGetCommentsAdminReport() throws Exception {
        LocalDate start = LocalDate.of(2026, 8, 1);
        LocalDate end = LocalDate.of(2026, 8, 31);
        when(reportService.nineReport(start, end)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/reports/comments/admin")
                        .param("startDate", start.toString())
                        .param("endDate", end.toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(reportService).nineReport(start, end);
    }

    @Test
    void testGetCommentsReportWhenDateRangeIsInvalid() throws Exception {
        mockMvc.perform(get("/v1/reports/comments")
                        .param("startDate", "2026-12-31")
                        .param("endDate", "2026-01-01"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("startDate must be before or equal to endDate"));

        verify(reportService, never()).firstEditorAdminReport(any(), any(), any());
    }
}
