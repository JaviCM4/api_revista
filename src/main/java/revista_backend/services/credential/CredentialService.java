package revista_backend.services.credential;

import revista_backend.dto.credential.CredentialResponse;
import revista_backend.dto.credential.CredentialResquest;
import revista_backend.dto.credential.JwtResponse;
import revista_backend.dto.credential.RecoverPasswordRequest;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.user.User;

public interface CredentialService {
    
    void create(User user, String username, String password)
            throws ConflictException;

    JwtResponse getLoginResponse(CredentialResquest credentialRequest)
            throws ResourceNotFoundException, ValidationException;

    void updateActiveVerification(Integer idUser)
            throws ResourceNotFoundException;

    /**
     * Starts password recovery flow by generating a token and sending it by email.
     */
    void recoverPassword(String email) throws ResourceNotFoundException;

    /**
     * Completes password recovery using the token and sets a new password.
     */
    void verifyPassword(RecoverPasswordRequest dto)
            throws ResourceNotFoundException, ValidationException;

    /**
     * After a login attempt when `activeVerification` is enabled, this method
     * will validate the temporary token and return a normal JWT.
     */
    JwtResponse verifyLoginToken(String tokenVerificacion)
            throws ResourceNotFoundException, ValidationException;
}

