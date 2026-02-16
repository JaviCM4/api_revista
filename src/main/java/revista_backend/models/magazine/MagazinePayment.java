package revista_backend.models.magazine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.user.User;

import java.time.LocalDate;

@Entity
@Table(name = "pago_revista")
@Data
@NoArgsConstructor
public class MagazinePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revista_id")
    @JsonIgnore
    private Magazine magazine;

    @Column(name = "pago")
    private int payment;

    @Column(name = "fecha_pago")
    private LocalDate paymentDate;
}
