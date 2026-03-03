package revista_backend.dto.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class RecoverPasswordRequest {

    @NotBlank
    @Size(max = 150)
    String tokenRecover;

    @NotBlank
    @Size(max = 150)
    String newPassword;

}
