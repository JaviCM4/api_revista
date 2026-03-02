package revista_backend.services.orchestrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revista_backend.dto.user.UserCreateRequest;
import revista_backend.dto.user.UserCreateResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.user.User;
import revista_backend.services.credential.CredentialServiceImplementation;
import revista_backend.services.user.UserServiceImplementation;

@Service
public class OrchestratorUserCredential {
    private final UserServiceImplementation userServiceImplementation;
    private final CredentialServiceImplementation credentialServiceImplementation;

    @Autowired
    public OrchestratorUserCredential(UserServiceImplementation userServiceImplementation, CredentialServiceImplementation credentialServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
        this.credentialServiceImplementation = credentialServiceImplementation;
    }

    @Transactional(rollbackFor = ConflictException.class)
    public UserCreateResponse createUserWithCredential(UserCreateRequest dto)
            throws ConflictException, ResourceNotFoundException {
        User newUser = userServiceImplementation.create(dto);
        credentialServiceImplementation.create(newUser, dto.getUsername(), dto.getPassword());
        return new UserCreateResponse(newUser.getId(), newUser.getNames(), newUser.getLastNames());
    }
}


