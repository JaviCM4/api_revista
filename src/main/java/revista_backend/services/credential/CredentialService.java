package revista_backend.services.credential;

import revista_backend.dto.credential.CredentialResponse;
import revista_backend.dto.credential.CredentialResquest;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.user.User;

public interface CredentialService {
    
    void create(User user, String username, String password) throws ConflictException;

    CredentialResponse getLoginResponse(CredentialResquest credentialRequest) throws ResourceNotFoundException;
}

