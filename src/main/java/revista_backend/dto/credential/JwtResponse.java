package revista_backend.dto.credential;

import lombok.Value;

@Value
public class JwtResponse {
    String token;
    Integer idRole;
    String username;
}
