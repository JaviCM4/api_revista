package revista_backend.models.magazine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "edicion")
@Data
@NoArgsConstructor
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revista_id")
    @JsonIgnore
    private Magazine magazine;

    @Column(name = "recurso")
    private String resource;

    @Column(name = "fecha_creacion")
    private LocalDate creationDate;
}
