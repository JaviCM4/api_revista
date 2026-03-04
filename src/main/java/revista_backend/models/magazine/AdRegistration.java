package revista_backend.models.magazine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.advertisement.Advertisement;

import java.time.LocalDate;

@Entity
@Table(name = "registro_anuncio")
@Data
@NoArgsConstructor
public class AdRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id")
    @JsonIgnore
    private Advertisement advertisement;

    @Column(name = "url")
    private String url;

    @Column(name = "cantidad_vistas")
    private Integer numberOfViews;

    @Column(name = "fecha_vista")
    private LocalDate dateView;
}
