package revista_backend.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.credential.*;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.credential.CredentialService;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController {

    private CredentialService credentialService;
    private final SecurityUtils securityUtils;

    @Autowired
    public AuthenticationController(CredentialService credentialService, SecurityUtils securityUtils) {
        this.credentialService = credentialService;
        this.securityUtils = securityUtils;
    }

    @PostMapping()
    @PermitAll
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody CredentialResquest dto)
            throws ResourceNotFoundException, ValidationException {
        JwtResponse response = credentialService.getLoginResponse(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-login")
    @PermitAll
    public ResponseEntity<JwtResponse> verifyLoginToken(@Valid @RequestBody LoginTokenRequest dto)
            throws ResourceNotFoundException, ValidationException {
        JwtResponse response = credentialService.verifyLoginToken(dto.getTokenVerification());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/first-login")
    @PermitAll
    public ResponseEntity<Void> firstLogin(@Valid @RequestBody FirstLoginRequest dto)
            throws ValidationException, ResourceNotFoundException {
        credentialService.verifyFirstLogin(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recover-password")
    @PermitAll
    public ResponseEntity<Void> recoverPassword(@Valid @RequestBody EmailRequest dto)
            throws ResourceNotFoundException {
        credentialService.recoverPassword(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-recover")
    @PermitAll
    public ResponseEntity<Void> verifyPassword(@Valid @RequestBody RecoverPasswordRequest dto)
            throws ResourceNotFoundException, ValidationException {
        credentialService.verifyPassword(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    @PermitAll
    public ResponseEntity<Void> toggleVerification(HttpServletRequest request)
            throws ResourceNotFoundException {
        int userId = securityUtils.getUserIdFromRequest(request);
        credentialService.updateActiveVerification(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idUser}")
    @PermitAll
    public ResponseEntity<Void> sendLoginToken(@PathVariable Integer idUser)
            throws ResourceNotFoundException, ConflictException {
        credentialService.sendLoginToken(idUser);
        return ResponseEntity.ok().build();
    }
}