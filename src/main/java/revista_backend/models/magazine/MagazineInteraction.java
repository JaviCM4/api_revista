package revista_backend.models.magazine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "interaccion_revista")
@Data
@NoArgsConstructor
public class MagazineInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revista_id")
    @JsonIgnore
    private Magazine magazine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private User user;

    @Column(name = "me_gusta")
    private Boolean liked;

    @Column(name = "fecha_me_gusta")
    private LocalDate likeDate;

    @Column(name = "comentario")
    private String comment;

    @Column(name = "fecha_comentario")
    private LocalDate commentDate;
}
