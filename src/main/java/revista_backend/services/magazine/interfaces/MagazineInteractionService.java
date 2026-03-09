package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.InteractionCommentRequest;
import revista_backend.dto.magazine.response.CommentsResponse;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;

import java.util.List;

public interface MagazineInteractionService {

    void createLike(Integer idMagazine, Integer idUser) throws ResourceNotFoundException, RestrictedException;

    void createComment(InteractionCommentRequest dto, Integer idUser)
            throws ResourceNotFoundException, RestrictedException;

    void deleteComment(Integer idComment) throws ResourceNotFoundException;

    boolean findLikeByUser(Integer idMagazine, Integer idUser);

    Integer findQuantityLikeByMagazine(Integer idMagazine);

    List<CommentsResponse> findAllCommentsByMagazine(Integer idMagazine);
}
