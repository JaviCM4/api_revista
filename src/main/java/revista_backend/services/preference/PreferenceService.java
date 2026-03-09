package revista_backend.services.preference;

import revista_backend.dto.preference.PreferenceFindResponse;
import revista_backend.exceptions.AccessDeniedException;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;

import java.util.List;

public interface PreferenceService {

    void create(Integer idCategoryPreference, Integer idUser)
            throws ResourceNotFoundException, ConflictException;

    List<PreferenceFindResponse> findAllByUser(Integer idUser)
            throws ResourceNotFoundException;

    void delete(Integer idPreference, Integer idUser)
            throws ResourceNotFoundException, AccessDeniedException;
}
