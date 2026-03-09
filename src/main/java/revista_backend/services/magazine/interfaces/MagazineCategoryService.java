package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.MagazineCategoryCreateRequest;
import revista_backend.dto.magazine.response.MagazineCategoryResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;

import java.util.List;

public interface MagazineCategoryService {

        void create(MagazineCategoryCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, ConflictException, AccessDeniedException;

    List<MagazineCategoryResponse> findByMagazineId(Integer idMagazine);

    void delete(Integer idMagazineCategorie, Integer idUser)
            throws ResourceNotFoundException, AccessDeniedException;
}
