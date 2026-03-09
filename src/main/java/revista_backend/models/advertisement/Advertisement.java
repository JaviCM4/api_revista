package revista_backend.models.advertisement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.user.User;
import revista_backend.models.types.AdType;
import revista_backend.models.status.AdStatus;

import java.time.LocalDate;

@Entity
@Table(name = "anuncio")
@Data
@NoArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_anuncio_id")
    @JsonIgnore
    private AdType adType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_anuncio_id")
    @JsonIgnore
    private AdStatus adStatus;

    @Column(name = "costo_total")
    private int totalCost;

    @Column(name = "fecha_creacion")
    private LocalDate creationDate;

    @Column(name = "fecha_vencimiento")
    private LocalDate expirationDate;

}
