package revista_backend.services.magazine.interfaces;

import revista_backend.dto.magazine.request.SubscriptionCreateRequest;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.RestrictedException;
import revista_backend.exceptions.ValidationException;

public interface MagazineSubscriptionService {

    void create(SubscriptionCreateRequest dto)
            throws ResourceNotFoundException, ConflictException, RestrictedException, ValidationException;

    void delete(SubscriptionCreateRequest dto) throws ResourceNotFoundException, ConflictException;
}
