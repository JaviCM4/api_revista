package revista_backend.models.status;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado_bloqueo_anuncio")
@Data
@NoArgsConstructor
public class AdLockStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String name;
}
