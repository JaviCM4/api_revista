package revista_backend.models.advertisement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "registro_anuncio")
@Data
@NoArgsConstructor
public class AdRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id")
    @JsonIgnore
    private Advertisement advertisement;

    @Column(name = "url")
    private String url;

    @Column(name = "cantidad_vistas")
    private Integer views;

    @Column(name = "fecha_vista")
    private LocalDate viewDate;
}
