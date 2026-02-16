package revista_backend.models.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.types.MagazineCategoryType;

@Entity
@Table(name = "categoria_revista")
@Data
@NoArgsConstructor
public class MagazineCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revista_id")
    @JsonIgnore
    private Magazine magazine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_categoria_revista_id")
    @JsonIgnore
    private MagazineCategoryType magazineCategoryType;

}
