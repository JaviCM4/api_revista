package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.magazine.request.MagazineCreateRequest;
import revista_backend.dto.magazine.request.MagazineUpdateCostRequest;
import revista_backend.dto.magazine.request.MagazineUpdatePermissionsRequest;
import revista_backend.dto.magazine.response.MagazineFindNormalResponse;
import revista_backend.dto.magazine.response.MagazineFindResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.magazine.MagazineServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/magazines")
public class MagazineController {

    private final MagazineServiceImplementation magazineService;
    private final SecurityUtils securityUtils;

    @Autowired
    public MagazineController(MagazineServiceImplementation magazineService, SecurityUtils securityUtils) {
        this.magazineService = magazineService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> create(@Valid @RequestBody MagazineCreateRequest dto, HttpServletRequest request)
            throws ResourceNotFoundException, ValidationException {
        int userId = securityUtils.getUserIdFromRequest(request);
        magazineService.create(dto, userId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/cost")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> updateCost(@Valid @RequestBody MagazineUpdateCostRequest dto)
            throws ResourceNotFoundException {
        magazineService.updateCostMagazine(dto);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/permissions")
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> updatePermissions(@Valid @RequestBody MagazineUpdatePermissionsRequest dto,
                                                  HttpServletRequest request)
            throws ResourceNotFoundException, AccessDeniedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        magazineService.updatePermissionsMagazine(dto, userId);
        return ResponseEntity.accepted().build();
    }

    // ---------------------------------------------------------------------------------

    @GetMapping
    @PermitAll
    public ResponseEntity<List<MagazineFindNormalResponse>> findAllNormal() {
        List<MagazineFindNormalResponse> list = magazineService.findAllNormal();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/category/{idCategory}")
    @PermitAll
    public ResponseEntity<List<MagazineFindNormalResponse>> findAllCategory(@PathVariable("idCategory") Integer idCategory) {
        List<MagazineFindNormalResponse> list = magazineService.findAllCategory(idCategory);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/tags/{tag}")
    @PermitAll
    public ResponseEntity<List<MagazineFindNormalResponse>> findAllTag(@PathVariable String tag) {
        List<MagazineFindNormalResponse> list = magazineService.findAllTag(tag);
        return ResponseEntity.ok(list);
    }

    // ---------------------------------------------------------------------------------

    @GetMapping("/editor")
    @RolesAllowed("EDITOR")
    public ResponseEntity<List<MagazineFindResponse>> findAllEditor(HttpServletRequest request) {
        int userId = securityUtils.getUserIdFromRequest(request);
        List<MagazineFindResponse> list = magazineService.findAllEditor(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/subscriber")
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<List<MagazineFindNormalResponse>> findAllSubscriber(HttpServletRequest request) {
        int userId = securityUtils.getUserIdFromRequest(request);
        List<MagazineFindNormalResponse> list = magazineService.findAllSubscriber(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<MagazineFindResponse>> findAllAdmin() {
        List<MagazineFindResponse> list = magazineService.findAllAdmin();
        return ResponseEntity.ok(list);
    }

}