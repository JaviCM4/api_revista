package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.advertisement.AdBlockCreateRequest;
import revista_backend.dto.advertisement.AdCreateRequest;
import revista_backend.dto.advertisement.AdFindResponse;
import revista_backend.exceptions.MoneyException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.advertisement.AdvertisementService;

import java.util.List;

@RestController
@RequestMapping("/v1/ads")
public class AdvertisementController {

    private final AdvertisementService adService;
    private final SecurityUtils securityUtils;

    @Autowired
    public AdvertisementController(AdvertisementService adService, SecurityUtils securityUtils) {
        this.adService = adService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    @RolesAllowed("ADVERTISER")
    public ResponseEntity<Void> create(@Valid @RequestBody AdCreateRequest dto, HttpServletRequest request)
            throws ResourceNotFoundException, ValidationException, MoneyException {
        int userId = securityUtils.getUserIdFromRequest(request);
        adService.create(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/block")
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> blockAd(@Valid @RequestBody AdBlockCreateRequest dto, HttpServletRequest request)
            throws ResourceNotFoundException, ValidationException, MoneyException, AccessDeniedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        adService.blockAd(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping("/{idAdvertisement}")
    @RolesAllowed({"ADVERTISER", "ADMIN"})
    public ResponseEntity<Void> disableAd(@PathVariable Integer idAdvertisement)
            throws ResourceNotFoundException {
        adService.disableAd(idAdvertisement);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/block/{idMagazine}")
    @RolesAllowed("EDITOR")
    public ResponseEntity<Integer> getBlockCostByMagazine(@PathVariable Integer idMagazine)
            throws ResourceNotFoundException {
        Integer cost = adService.getBlockCostByMagazine(idMagazine);
        return ResponseEntity.status(HttpStatus.OK).body(cost);
    }

    @GetMapping("/advertiser")
    @RolesAllowed("ADVERTISER")
    public ResponseEntity<List<AdFindResponse>> findAllByAdvertiser(HttpServletRequest request) {
        int userId = securityUtils.getUserIdFromRequest(request);
        List<AdFindResponse> list = adService.findAllByAdvertiser(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/magazine/{idMagazine}")
    @PermitAll
    public ResponseEntity<List<AdFindResponse>> findAllByMagazine(@PathVariable Integer idMagazine)
            throws ResourceNotFoundException {
        List<AdFindResponse> list = adService.findAllAdByMagazine(idMagazine);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/magazine")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<AdFindResponse>> findAllAdvertisement() {
        List<AdFindResponse> list = adService.findAllAdvertisement();
        return ResponseEntity.ok(list);
    }
}