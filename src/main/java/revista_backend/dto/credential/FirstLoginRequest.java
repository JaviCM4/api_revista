package revista_backend.dto.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class FirstLoginRequest {

    @NotBlank
    @Size(max = 75)
    String username;

    @NotBlank
    @Size(max = 150)
    String password;

    @NotBlank
    @Size(max = 250)
    String token;
}
