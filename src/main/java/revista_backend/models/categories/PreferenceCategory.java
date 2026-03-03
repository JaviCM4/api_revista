package revista_backend.models.categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.types.MagazineCategoryType;
import revista_backend.models.types.PreferenceType;

@Entity
@Table(name = "categoria_preferencia")
@Data
@NoArgsConstructor
public class PreferenceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_categoria_revista_id")
    @JsonIgnore
    private MagazineCategoryType magazineCategoryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_preferencia_id")
    @JsonIgnore
    private PreferenceType preferenceType;

    @Column(name = "nombre")
    private String name;
}
