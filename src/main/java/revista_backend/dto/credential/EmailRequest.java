package revista_backend.dto.credential;

import jakarta.validation.constraints.Email;
import lombok.Value;

@Value
public class EmailRequest {

    @Email
    String email;
}