package revista_backend.dto.user;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
public class UserUpdateRequest {

    @NotBlank
    @Size(max = 100)
    String names;

    @NotBlank
    @Size(max = 100)
    String lastNames;

    @Past
    LocalDate dateOfBirth;

    @Size(max = 255)
    String photography;

    @Size(max = 500)
    String description;
    
}
