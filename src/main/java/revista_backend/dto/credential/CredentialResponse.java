package revista_backend.dto.credential;

import lombok.Value;

@Value
public class CredentialResponse {
    Integer id;
    String username;
    boolean activeVerification;
}
