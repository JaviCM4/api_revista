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
    private Integer id;

    @Column(name = "costo")
    private Integer cost;

    @Column(name = "dias")
    private Integer days;
}
