package revista_backend.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.magazine.request.MagazineCategoryCreateRequest;
import revista_backend.dto.magazine.response.MagazineCategoryResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.magazine.MagazineCategoryServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/categories/magazines")
public class MagazineCategoryController {

    private final MagazineCategoryServiceImplementation categoryService;
    private final SecurityUtils securityUtils;

    @Autowired
    public MagazineCategoryController(MagazineCategoryServiceImplementation categoryService,
                                      SecurityUtils securityUtils) {
        this.categoryService = categoryService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> create(@Valid @RequestBody MagazineCategoryCreateRequest dto,
                                       HttpServletRequest request)
            throws ResourceNotFoundException, ConflictException, AccessDeniedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        categoryService.create(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{idMagazine}")
    @RolesAllowed("EDITOR")
    public ResponseEntity<List<MagazineCategoryResponse>> findByMagazineId(@PathVariable Integer idMagazine) {
        List<MagazineCategoryResponse> response = categoryService.findByMagazineId(idMagazine);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idMagazineCategory}")
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> delete(@PathVariable Integer idMagazineCategory,
                                       HttpServletRequest request)
            throws ResourceNotFoundException, AccessDeniedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        categoryService.delete(idMagazineCategory, userId);
        return ResponseEntity.noContent().build();
    }
}
