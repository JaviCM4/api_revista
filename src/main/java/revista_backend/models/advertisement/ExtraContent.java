package revista_backend.models.advertisement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contenido_extra")
@Data
@NoArgsConstructor
public class ExtraContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id")
    @JsonIgnore
    private Advertisement advertisement;

    @Column(name = "recurso")
    private String resource;
}
