package revista_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import revista_backend.CommonMvcTest;
import revista_backend.dto.magazine.request.MagazineCreateRequest;
import revista_backend.dto.magazine.request.MagazineUpdateCostRequest;
import revista_backend.dto.magazine.request.MagazineUpdatePermissionsRequest;
import revista_backend.exceptionhandler.ControllerExceptionHandler;
import revista_backend.security.SecurityUtils;
import revista_backend.services.magazine.MagazineServiceImplementation;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MagazineController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {MagazineController.class, ControllerExceptionHandler.class})
public class MagazineControllerTest extends CommonMvcTest {

    private static final int USER_ID = 11;

    @MockBean
    private MagazineServiceImplementation magazineService;

    @MockBean
    private SecurityUtils securityUtils;

    @Test
    void testCreateMagazine() throws Exception {
        MagazineCreateRequest request = new MagazineCreateRequest(
                "Tech Weekly",
                "Magazine about software",
                true,
                true,
                true,
                LocalDate.now()
        );

        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);

        mockMvc.perform(post("/v1/magazines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        verify(magazineService).create(any(MagazineCreateRequest.class), eq(USER_ID));
    }

    @Test
    void testUpdateCost() throws Exception {
        MagazineUpdateCostRequest request = new MagazineUpdateCostRequest(1, 20, 30);

        mockMvc.perform(put("/v1/magazines/cost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        verify(magazineService).updateCostMagazine(any(MagazineUpdateCostRequest.class));
    }

    @Test
    void testUpdatePermissions() throws Exception {
        MagazineUpdatePermissionsRequest request = new MagazineUpdatePermissionsRequest(1, true, false, true);
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);

        mockMvc.perform(put("/v1/magazines/permissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());

        verify(magazineService).updatePermissionsMagazine(any(MagazineUpdatePermissionsRequest.class), eq(USER_ID));
    }

    @Test
    void testFindAllNormal() throws Exception {
        when(magazineService.findAllNormal()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/magazines"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(magazineService).findAllNormal();
    }

    @Test
    void testFindAllCategory() throws Exception {
        when(magazineService.findAllCategory(5)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/magazines/category/{idCategory}", 5))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(magazineService).findAllCategory(5);
    }

    @Test
    void testFindAllTag() throws Exception {
        when(magazineService.findAllTag("java")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/magazines/tags/{tag}", "java"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(magazineService).findAllTag("java");
    }

    @Test
    void testFindAllEditor() throws Exception {
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);
        when(magazineService.findAllEditor(USER_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/magazines/editor"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(magazineService).findAllEditor(USER_ID);
    }

    @Test
    void testFindAllSubscriber() throws Exception {
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);
        when(magazineService.findAllSubscriber(USER_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/magazines/subscriber"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(magazineService).findAllSubscriber(USER_ID);
    }

    @Test
    void testFindAllAdmin() throws Exception {
        when(magazineService.findAllAdmin()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/magazines/admin"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(magazineService).findAllAdmin();
    }

    @Test
    void testCreateMagazineWhenCreateDateIsFuture() throws Exception {
        MagazineCreateRequest request = new MagazineCreateRequest(
                "Tech Weekly",
                "Magazine about software",
                true,
                true,
                true,
                LocalDate.now().plusDays(2)
        );

        mockMvc.perform(post("/v1/magazines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(magazineService, never()).create(any(MagazineCreateRequest.class), any(Integer.class));
    }
}
