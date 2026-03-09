package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.preference.PreferenceCategoryFindResponse;
import revista_backend.dto.preference.PreferenceCreateResquest;
import revista_backend.dto.preference.PreferenceFindResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.preference.PreferenceCategoryService;
import revista_backend.services.preference.PreferenceServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/preferences")
public class PreferenceController {

    private final PreferenceServiceImplementation preferenceService;
    private final PreferenceCategoryService categoryService;
    private final SecurityUtils securityUtils;

    @Autowired
    public PreferenceController(PreferenceServiceImplementation preferenceService, PreferenceCategoryService categoryService, SecurityUtils securityUtils) {
        this.preferenceService = preferenceService;
        this.categoryService = categoryService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<Void> create(@Valid @RequestBody PreferenceCreateResquest dto, HttpServletRequest request)
            throws ResourceNotFoundException, ConflictException {
        int userId = securityUtils.getUserIdFromRequest(request);
        preferenceService.create(dto.getIdCategoryPreference(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<List<PreferenceFindResponse>> findByUser(HttpServletRequest request)
            throws ResourceNotFoundException {
        int userId = securityUtils.getUserIdFromRequest(request);
        List<PreferenceFindResponse> list = preferenceService.findAllByUser(userId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/options")
    @PermitAll
    public ResponseEntity<List<PreferenceCategoryFindResponse>> findAllPreferenceAndCategories() {
        List<PreferenceCategoryFindResponse> list = categoryService.findAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{idPreference}")
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<Void> delete(@PathVariable Integer idPreference,
                                       HttpServletRequest request)
            throws ResourceNotFoundException, AccessDeniedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        preferenceService.delete(idPreference, userId);
        return ResponseEntity.noContent().build();
    }
}