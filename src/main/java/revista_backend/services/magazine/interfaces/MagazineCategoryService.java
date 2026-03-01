package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.MagazineCategoryCreateRequest;
import revista_backend.dto.magazine.response.MagazineCategoryResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;

import java.util.List;

public interface MagazineCategoryService {

    void create(MagazineCategoryCreateRequest dto) throws ResourceNotFoundException, ConflictException;

    List<MagazineCategoryResponse> findByMagazineId(Integer idMagazine);

    void delete(Integer idMagazineCategorie) throws ResourceNotFoundException;
}
