package revista_backend.models.magazine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etiqueta_revista")
@Data
@NoArgsConstructor
public class MagazineTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revista_id")
    @JsonIgnore
    private Magazine magazine;

    @Column(name = "detalle")
    private String detail;

}
