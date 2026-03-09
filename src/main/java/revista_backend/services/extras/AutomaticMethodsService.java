package revista_backend.services.extras;

import revista_backend.exceptions.ResourceNotFoundException;

public interface AutomaticMethodsService {

    void verifyDateAds() throws ResourceNotFoundException;

    void verifyDateBlocksAds() throws ResourceNotFoundException;

}
