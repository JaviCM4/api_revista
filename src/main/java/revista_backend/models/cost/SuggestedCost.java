package revista_backend.models.cost;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.types.CostType;

@Entity
@Table(name = "costo_sugerido")
@Data
@NoArgsConstructor
public class SuggestedCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_costo_id")
    @JsonIgnore
    private CostType costType;

    @Column(name = "costo")
    private int cost;

    @Column(name = "dias")
    private int days;
}
