package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.EditionCreateRequest;
import revista_backend.dto.magazine.response.EditionFindResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.magazine.Edition;

public interface EditionService {

    Edition create(EditionCreateRequest dto, Integer idUser)
            throws ResourceNotFoundException, AccessDeniedException;

    EditionFindResponse findAllEditionsByMagazine(Integer idMagazine) throws ResourceNotFoundException;

    void delete(Integer idEdition) throws ResourceNotFoundException;

}
