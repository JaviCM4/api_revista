package revista_backend.models.cost;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "costo_sugerido")
@Data
@NoArgsConstructor
public class SuggestedCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "costo")
    private Integer cost;

    @Column(name = "dias")
    private Integer days;
}
