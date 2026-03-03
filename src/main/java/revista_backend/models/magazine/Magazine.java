package revista_backend.models.magazine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "revista")
@Data
@NoArgsConstructor
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private User user;

    @Column(name = "titulo")
    private String title;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "permitir_suscripcion")
    private boolean allowSubscription;

    @Column(name = "permitir_comentarios")
    private boolean allowComments;

    @Column(name = "permitir_reacciones")
    private boolean allowReactions;

    @Column(name = "revista_activa")
    private boolean activeMagazine;

    @Column(name = "costo_dia")
    private Integer dailyCost;

    @Column(name = "costo_bloqueo_anuncio")
    private Integer adBlockCost;

    @Column(name = "fecha_creacion")
    private LocalDate creationDate;
}
