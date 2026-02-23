package revista_backend.services.credential;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import revista_backend.dto.credential.CredentialResponse;
import revista_backend.dto.credential.CredentialResquest;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.credential.Credential;
import revista_backend.models.user.User;
import revista_backend.repositories.credential.CredentialRepository;

@Service
public class CredentialServiceImplementation implements CredentialService {

    private final CredentialRepository credentialRepository;

    @Autowired
    public CredentialServiceImplementation(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    @Override
    public void create(User user, String username, String password) throws ConflictException {

        if (credentialRepository.existsByUsername(username)) {
            throw new ConflictException("El username '" + username + "' ya está registrado. Por favor, usa otro.");
        }

        Credential credential = new Credential();
        credential.setUser(user);
        credential.setUsername(username);
        credential.setPassword(password);

        credentialRepository.save(credential);
    }

    @Override
    public CredentialResponse getLoginResponse(CredentialResquest dto) throws ResourceNotFoundException {

        if (!credentialRepository.existsByUsernameAndPassword(dto.getUsername(), dto.getPassword())) {
            throw new ResourceNotFoundException("Username o Password incorrecto");
        }
        
        Credential credential = credentialRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new CredentialResponse(credential.getId(), credential.getUsername());
    }

}


