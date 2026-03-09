package revista_backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import revista_backend.CommonMvcTest;
import revista_backend.dto.advertisement.AdBlockCreateRequest;
import revista_backend.dto.advertisement.AdCreateRequest;
import revista_backend.exceptionhandler.ControllerExceptionHandler;
import revista_backend.security.SecurityUtils;
import revista_backend.services.advertisement.AdvertisementService;

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

@WebMvcTest(controllers = AdvertisementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {AdvertisementController.class, ControllerExceptionHandler.class})
public class AdvertisementControllerTest extends CommonMvcTest {

    private static final int USER_ID = 22;

    @MockBean
    private AdvertisementService adService;

    @MockBean
    private SecurityUtils securityUtils;

    @Test
    void testCreateAd() throws Exception {
        AdCreateRequest request = new AdCreateRequest(
                1,
                Collections.singletonList("https://cdn/ad.png"),
                150,
                LocalDate.now().plusDays(5)
        );

        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);

        mockMvc.perform(post("/v1/ads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(adService).create(any(AdCreateRequest.class), eq(USER_ID));
    }

    @Test
    void testBlockAd() throws Exception {
        AdBlockCreateRequest request = new AdBlockCreateRequest(
                10,
                5,
                200,
                LocalDate.now().plusDays(7)
        );

        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);

        mockMvc.perform(post("/v1/ads/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(adService).blockAd(any(AdBlockCreateRequest.class), eq(USER_ID));
    }

    @Test
    void testDisableAd() throws Exception {
        mockMvc.perform(put("/v1/ads/{idAdvertisement}", 55))
                .andExpect(status().isOk());

        verify(adService).disableAd(55);
    }

    @Test
    void testGetBlockCostByMagazine() throws Exception {
        when(adService.getBlockCostByMagazine(9)).thenReturn(300);

        mockMvc.perform(get("/v1/ads/block/{idMagazine}", 9))
                .andExpect(status().isOk())
                .andExpect(content().string("300"));

        verify(adService).getBlockCostByMagazine(9);
    }

    @Test
    void testFindAllByAdvertiser() throws Exception {
        when(securityUtils.getUserIdFromRequest(any(HttpServletRequest.class))).thenReturn(USER_ID);
        when(adService.findAllByAdvertiser(USER_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/ads/advertiser"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(adService).findAllByAdvertiser(USER_ID);
    }

    @Test
    void testFindAllByMagazine() throws Exception {
        when(adService.findAllAdByMagazine(9)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/ads/magazine/{idMagazine}", 9))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(adService).findAllAdByMagazine(9);
    }

    @Test
    void testFindAllAdvertisement() throws Exception {
        when(adService.findAllAdvertisement()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/ads/magazine"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Collections.emptyList())));

        verify(adService).findAllAdvertisement();
    }

    @Test
    void testCreateAdWhenExpirationDateIsPast() throws Exception {
        AdCreateRequest request = new AdCreateRequest(
                1,
                Collections.singletonList("https://cdn/ad.png"),
                150,
                LocalDate.now().minusDays(1)
        );

        mockMvc.perform(post("/v1/ads")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(adService, never()).create(any(AdCreateRequest.class), any(Integer.class));
    }
}
