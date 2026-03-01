package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.MagazineTagCreateRequest;
import revista_backend.dto.magazine.response.MagazineTagResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;

import java.util.List;

public interface MagazineTagService {

    void create(MagazineTagCreateRequest dto) throws ResourceNotFoundException, ConflictException;

    List<MagazineTagResponse> findByMagazineTagId(Integer idMagazine);

    void delete(Integer idTag) throws ResourceNotFoundException;
}
