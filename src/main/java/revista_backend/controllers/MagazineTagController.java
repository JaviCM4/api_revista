package revista_backend.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.magazine.request.MagazineTagCreateRequest;
import revista_backend.dto.magazine.response.MagazineTagResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.services.magazine.MagazineTagServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/tags")
public class MagazineTagController {

    private final MagazineTagServiceImplementation tagService;

    @Autowired
    public MagazineTagController(MagazineTagServiceImplementation tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> create(@Valid @RequestBody MagazineTagCreateRequest dto)
            throws ResourceNotFoundException, ConflictException {
        tagService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{idMagazine}")
    @RolesAllowed("EDITOR")
    public ResponseEntity<List<MagazineTagResponse>> findByMagazineTagId(@PathVariable Integer idMagazine) {
        List<MagazineTagResponse> response = tagService.findByMagazineTagId(idMagazine);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{idTag}")
    @RolesAllowed("EDITOR")
    public ResponseEntity<Void> delete(@PathVariable Integer idTag)
            throws ResourceNotFoundException {
        tagService.delete(idTag);
        return ResponseEntity.noContent().build();
    }
}