package revista_backend.services.orchestrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revista_backend.dto.user.UserCreateAdminRequest;
import revista_backend.dto.user.UserCreateRequest;
import revista_backend.dto.user.UserCreateResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.user.User;
import revista_backend.services.contact.ContactServiceImplementation;
import revista_backend.services.credential.CredentialServiceImplementation;
import revista_backend.services.user.UserServiceImplementation;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrchestratorUserCredentialServiceImplementation implements OrchestratorUserCredentialService {

    private final UserServiceImplementation userServiceImplementation;
    private final CredentialServiceImplementation credentialServiceImplementation;
    private final ContactServiceImplementation contactServiceImplementation;

    @Autowired
    public OrchestratorUserCredentialServiceImplementation(UserServiceImplementation userServiceImplementation, CredentialServiceImplementation credentialServiceImplementation, ContactServiceImplementation contactServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
        this.credentialServiceImplementation = credentialServiceImplementation;
        this.contactServiceImplementation = contactServiceImplementation;
    }

    @Override
    public UserCreateResponse createUserWithCredential(UserCreateRequest dto)
            throws ConflictException, ResourceNotFoundException {
        User newUser = userServiceImplementation.create(dto);
        contactServiceImplementation.create(newUser, dto.getPhone(), dto.getEmail());
        credentialServiceImplementation.create(newUser, dto.getUsername(), dto.getPassword());
        return new UserCreateResponse(newUser.getId(), newUser.getNames(), newUser.getLastNames());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserCreateResponse createUserWithCredentialAdmin(UserCreateAdminRequest dto)
            throws ConflictException, ResourceNotFoundException {
        User newUser = userServiceImplementation.createAdmin(dto);
        contactServiceImplementation.create(newUser, dto.getPhone(), dto.getEmail());
        credentialServiceImplementation.createAdmin(newUser, dto.getUsername(), dto.getEmail());
        return new UserCreateResponse(newUser.getId(), newUser.getNames(), newUser.getLastNames());
    }
}
