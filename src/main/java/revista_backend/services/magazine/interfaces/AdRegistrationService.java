package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.AdRegistrationCreateRequest;
import revista_backend.exceptions.ResourceNotFoundException;

public interface AdRegistrationService {

    void create(AdRegistrationCreateRequest dto) throws ResourceNotFoundException;
}
