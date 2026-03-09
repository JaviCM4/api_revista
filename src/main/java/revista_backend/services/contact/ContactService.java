package revista_backend.services.contact;

import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.user.User;

public interface ContactService {

    void create(User user, Integer phone, String email)
            throws ResourceNotFoundException, ConflictException;

}
