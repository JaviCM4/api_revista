package revista_backend.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.location.Municipality;
import revista_backend.models.status.UserStatus;
import revista_backend.models.types.SexType;
import revista_backend.models.types.UserType;

import java.time.LocalDate;
import revista_backend.dto.user.UserUpdateRequest;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_usuario_id")
    @JsonIgnore
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_usuario_id")
    @JsonIgnore
    private UserStatus userStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_sexo_id")
    @JsonIgnore
    private SexType sexType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipio_id")
    @JsonIgnore
    private Municipality municipality;

    @Column(name = "nombres")
    private String names;

    @Column(name = "apellidos")
    private String lastNames;

    @Column(name = "fecha_nacimiento")
    private LocalDate dateOfBirth;

    @Column(name = "fotografia")
    private String photography;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "dinero_disponible")
    private Integer availableMoney;
    
    public void updateEntity(UserUpdateRequest dto) {
        this.names = dto.getNames();
        this.lastNames = dto.getLastNames();
        this.dateOfBirth = dto.getDateOfBirth();
        this.photography = dto.getPhotography();
        this.description = dto.getDescription();
    }
}
