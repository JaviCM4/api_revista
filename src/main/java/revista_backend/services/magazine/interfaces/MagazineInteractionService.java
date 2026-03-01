package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.InteractionCommentRequest;
import revista_backend.dto.magazine.request.InteractionLikeRequest;
import revista_backend.dto.magazine.response.CommentsResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;

import java.util.List;

public interface MagazineInteractionService {

    void createLike(InteractionLikeRequest dto) throws ResourceNotFoundException, RestrictedException;

    void createComment(InteractionCommentRequest dto) throws ResourceNotFoundException, ConflictException,RestrictedException;

    List<CommentsResponse> findAllCommentsByMagazine(Integer idMagazine);
}
