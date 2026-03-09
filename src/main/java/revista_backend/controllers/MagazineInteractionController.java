package revista_backend.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import revista_backend.dto.magazine.request.InteractionCommentRequest;
import revista_backend.dto.magazine.request.InteractionLikeRequest;
import revista_backend.dto.magazine.response.CommentsResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.security.SecurityUtils;
import revista_backend.services.magazine.MagazineInteractionServiceImplementation;

import java.util.List;

@RestController
@RequestMapping("/v1/interaction")
public class MagazineInteractionController {

    private final MagazineInteractionServiceImplementation interactionService;
    private final SecurityUtils securityUtils;

    @Autowired
    public MagazineInteractionController(MagazineInteractionServiceImplementation interactionService, SecurityUtils securityUtils) {
        this.interactionService = interactionService;
        this.securityUtils = securityUtils;
    }

    @PostMapping("/like")
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<Void> createLike(@Valid @RequestBody InteractionLikeRequest dto, HttpServletRequest request)
            throws ResourceNotFoundException, RestrictedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        interactionService.createLike(dto.getIdMagazine(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/like/{idMagazine}")
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<Boolean> findLikeByUser(@PathVariable Integer idMagazine, HttpServletRequest request) {
        int userId = securityUtils.getUserIdFromRequest(request);
        boolean response = interactionService.findLikeByUser(idMagazine, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/comment")
    @RolesAllowed("SUBSCRIBER")
    public ResponseEntity<Void> createComments(@Valid @RequestBody InteractionCommentRequest dto, HttpServletRequest request)
            throws ResourceNotFoundException, RestrictedException {
        int userId = securityUtils.getUserIdFromRequest(request);
        interactionService.createComment(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/comment/{idMagazine}")
    @RolesAllowed({"SUBSCRIBER", "ADMIN", "EDITOR"})
    public ResponseEntity<List<CommentsResponse>> findAllCommentsByMagazine(@PathVariable Integer idMagazine) {
        List<CommentsResponse> response = interactionService.findAllCommentsByMagazine(idMagazine);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/comment/{idComment}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer idComment)
            throws ResourceNotFoundException {
        interactionService.deleteComment(idComment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
