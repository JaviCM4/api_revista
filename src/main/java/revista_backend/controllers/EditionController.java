package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.magazine.request.EditionCreateRequest;
import revista_backend.dto.magazine.response.EditionFindResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.security.SecurityUtils;
import revista_backend.models.magazine.Edition;
import revista_backend.services.magazine.EditionServiceImplementation;

@RestController
@RequestMapping("/v1/editions")
public class EditionController {

    private final EditionServiceImplementation editionService;
    private final SecurityUtils securityUtils;

    @Autowired
    public EditionController(EditionServiceImplementation editionService, SecurityUtils securityUtils) {
        this.editionService = editionService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> create(@Valid @RequestBody EditionCreateRequest dto, HttpServletRequest request)
            throws ResourceNotFoundException, AccessDeniedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        editionService.create(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{idMagazine}")
    @PermitAll
    public ResponseEntity<EditionFindResponse> findAllEditionsByMagazine(@PathVariable Integer idMagazine)
            throws ResourceNotFoundException {
        EditionFindResponse resources = editionService.findAllEditionsByMagazine(idMagazine);
        return ResponseEntity.ok(resources);
    }

    @DeleteMapping("/{idEdition}")
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> deleteEdition(@PathVariable Integer idEdition)
        throws ResourceNotFoundException {
        editionService.delete(idEdition);
        return ResponseEntity.noContent().build();
    }
}
