package revista_backend.dto.user;

import jakarta.validation.constraints.*;
import lombok.Value;
import revista_backend.models.location.Municipality;
import revista_backend.models.status.UserStatus;
import revista_backend.models.types.SexType;
import revista_backend.models.types.UserType;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Value
public class UserCreateRequest {

    @NotNull
    @Min(1)
    Integer  userStatus;

    @NotNull
    @Min(1)
    Integer  sexType;

    @NotNull
    @Min(1)
    Integer  municipioId;

    @NotBlank
    @Size(max = 50)
    String username;

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

    @NotNull
    @Min(1)
    Integer phone;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email inválido")
    String email;

    @NotBlank 
    @Size(min = 8, max = 100)
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
        message = "La contraseña mal Implementada"
    )
    String password;
    

    public User createEntity(UserType userType, UserStatus userStatus, SexType sexType, Municipality municipality) {
        User user = new User();
        user.setUserType(userType);
        user.setUserStatus(userStatus);
        user.setSexType(sexType);
        user.setMunicipality(municipality);
        user.setNames(this.getNames());
        user.setLastNames(this.getLastNames());
        user.setDateOfBirth(this.getDateOfBirth());
        user.setPhotography(this.getPhotography());
        user.setDescription(this.getDescription());
        user.setAvailableMoney(0);
        return user;
    }
}

