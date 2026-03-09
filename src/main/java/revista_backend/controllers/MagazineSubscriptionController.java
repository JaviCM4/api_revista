package revista_backend.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.magazine.request.SubscriptionCreateRequest;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.exceptions.ValidationException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.magazine.MagazineSubscriptionServiceImplementation;

@RestController
@RequestMapping("/v1/subscriptions")
public class MagazineSubscriptionController {

    private final MagazineSubscriptionServiceImplementation subscriptionService;
    private final SecurityUtils securityUtils;

    @Autowired
    public MagazineSubscriptionController(MagazineSubscriptionServiceImplementation subscriptionService,
                                          SecurityUtils securityUtils) {
        this.subscriptionService = subscriptionService;
        this.securityUtils = securityUtils;
    }

    @PostMapping
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<Void> create(@Valid @RequestBody SubscriptionCreateRequest dto,
                                       HttpServletRequest request)
            throws ResourceNotFoundException, ConflictException, ValidationException, RestrictedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        subscriptionService.create(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{idMagazine}")
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<Void> delete(@PathVariable Integer idMagazine,
                                       HttpServletRequest request)
            throws ResourceNotFoundException, ConflictException {
        int userId = securityUtils.getUserIdFromRequest(request);
        subscriptionService.delete(idMagazine, userId);
        return ResponseEntity.noContent().build();
    }
}