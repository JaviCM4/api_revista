
package revista_backend.controllers;


import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.user.*;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.orchestrator.OrchestratorUserCredentialService;
import revista_backend.services.orchestrator.OrchestratorUserCredentialServiceImplementation;
import revista_backend.services.user.UserService;
import revista_backend.services.user.UserServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final OrchestratorUserCredentialService orchestrator;
    private final UserService userService;
    private final SecurityUtils securityUtils;

    @Autowired
    public UserController(OrchestratorUserCredentialService orchestrator,
                          UserService userService,
                          SecurityUtils securityUtils) {
        this.orchestrator = orchestrator;
        this.userService = userService;
        this.securityUtils = securityUtils;
    }

    @PostMapping()
    @PermitAll
    public ResponseEntity<UserCreateResponse> createUserAndCredential(@Valid @RequestBody UserCreateRequest userCreateRequest)
            throws ConflictException, ResourceNotFoundException   {
        UserCreateResponse response = orchestrator.createUserWithCredential(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/admin")
    @RolesAllowed("ADMIN")
    public ResponseEntity<UserCreateResponse> createUserAndCredentialAdmin(@Valid @RequestBody UserCreateAdminRequest userCreateRequest)
            throws ConflictException, ResourceNotFoundException   {
        UserCreateResponse response = orchestrator.createUserWithCredentialAdmin(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list")
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<UserFindResponse>> findAllUsers() {
        List<UserFindResponse> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping()
    @PermitAll
    public ResponseEntity<UserFindResponse> findById(HttpServletRequest request)
            throws ResourceNotFoundException {
        int userId = securityUtils.getUserIdFromRequest(request);
        UserFindResponse user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping()
    @PermitAll
    public UserUpdateResponse updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request)
            throws ResourceNotFoundException{
        int userId = securityUtils.getUserIdFromRequest(request);
        return userService.update(userUpdateRequest, userId);
    }

    @PutMapping("/{money}")
    @PermitAll
    public ResponseEntity<Void> updateMoney(@PathVariable("money") Integer money, HttpServletRequest request)
            throws ResourceNotFoundException, ValidationException {
        int userId = securityUtils.getUserIdFromRequest(request);
        userService.updateMoney(money, userId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{idUser}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> suspendUser(@PathVariable("idUser") Integer idUser)
            throws ResourceNotFoundException{
        userService.suspendUser(idUser);
        return ResponseEntity.accepted().build();
    }

}