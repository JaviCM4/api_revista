package revista_backend.dto.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class LoginTokenRequest {

    @NotBlank
    @Size(max = 150)
    String tokenVerification;
}