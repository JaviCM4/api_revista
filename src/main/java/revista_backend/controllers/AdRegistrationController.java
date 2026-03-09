package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import revista_backend.dto.magazine.request.AdRegistrationCreateRequest;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.services.magazine.AdRegistrationServiceImplementation;

@RestController
@RequestMapping("/v1/ads/registrations")
public class AdRegistrationController {

    private final AdRegistrationServiceImplementation adRegistrationService;

    @Autowired
    public AdRegistrationController(AdRegistrationServiceImplementation adRegistrationService) {
        this.adRegistrationService = adRegistrationService;
    }

    @PostMapping
    @PermitAll
    public ResponseEntity<Void> create(@Valid @RequestBody AdRegistrationCreateRequest dto)
            throws ResourceNotFoundException {
        adRegistrationService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
