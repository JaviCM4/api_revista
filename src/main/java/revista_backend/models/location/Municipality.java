package revista_backend.models.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "municipio")
@Data
@NoArgsConstructor
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departamento_id")
    @JsonIgnore
    private Department department;

    @Column(name = "nombre")
    private String name;
}
