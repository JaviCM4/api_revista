package revista_backend.services.orchestrator;

import revista_backend.dto.user.UserCreateAdminRequest;
import revista_backend.dto.user.UserCreateRequest;
import revista_backend.dto.user.UserCreateResponse;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;

public interface OrchestratorUserCredentialService {

    UserCreateResponse createUserWithCredential(UserCreateRequest dto)
	    throws ConflictException, ResourceNotFoundException;

    UserCreateResponse createUserWithCredentialAdmin(UserCreateAdminRequest dto)
	    throws ConflictException, ResourceNotFoundException;
}
