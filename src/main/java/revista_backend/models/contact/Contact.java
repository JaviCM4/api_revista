package revista_backend.models.contact;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.types.ContactType;
import revista_backend.models.user.User;

@Entity
@Table(name = "contacto")
@Data
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_contacto_id")
    @JsonIgnore
    ContactType contactType;

    @Column(name = "detalle")
    String detail;
}
