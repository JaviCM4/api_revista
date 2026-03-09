package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.cost.SuggestedCostCreateResquest;
import revista_backend.dto.cost.SuggestedCostResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.services.cost.SuggestedCostServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/suggested-costs")
public class SuggestedCostController {

    private final SuggestedCostServiceImplementation costService;

    @Autowired
    public SuggestedCostController(SuggestedCostServiceImplementation costService) {
        this.costService = costService;
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> create(@Valid @RequestBody SuggestedCostCreateResquest dto)
            throws ResourceNotFoundException, ConflictException {
        costService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<List<SuggestedCostResponse>> findAll() {
        return ResponseEntity.ok(costService.findAll());
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws ResourceNotFoundException {
        costService.delete(id);
        return ResponseEntity.noContent().build();
    }
}