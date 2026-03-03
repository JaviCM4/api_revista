package revista_backend.dto.credential;

import lombok.Value;

@Value
public class JwtResponse {
    String token;
    Integer idRol;
    String username;
}
