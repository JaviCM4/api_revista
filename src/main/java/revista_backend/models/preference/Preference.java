package revista_backend.models.preference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.categories.PreferenceCategory;
import revista_backend.models.user.User;

@Entity
@Table(name = "preferencia")
@Data
@NoArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_preferencia_id")
    @JsonIgnore
    private PreferenceCategory preferenceCategory;

}
