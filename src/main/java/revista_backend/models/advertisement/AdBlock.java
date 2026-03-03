package revista_backend.models.advertisement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.status.AdLockStatus;

import java.time.LocalDate;

@Entity
@Table(name = "bloqueo_anuncio")
@Data
@NoArgsConstructor
public class AdBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revista_id")
    @JsonIgnore
    private Magazine magazine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id")
    @JsonIgnore
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_bloqueo_anuncio_id")
    @JsonIgnore
    private AdLockStatus adLockStatus;

    @Column(name = "pago")
    private int payment;

    @Column(name = "fecha_inicio_bloqueo")
    private LocalDate startDate;

    @Column(name = "fecha_fin_bloqueo")
    private LocalDate endDate;
}
