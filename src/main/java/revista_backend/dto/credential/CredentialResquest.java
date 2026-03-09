package revista_backend.dto.credential;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class CredentialResquest {

    @NotBlank
    @Size(max = 75)
    String username;

    @NotBlank
    @Size(max = 75)
    String password;

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
