package revista_backend.models.credential;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import revista_backend.models.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "credencial")
@Data
@NoArgsConstructor
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private User user;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "token_recuperacion")
    private String tokenRecovery;

    @Column(name = "fecha_fin_recuperacion")
    private LocalDateTime recoveryEndDate;

    @Column(name = "token_verifcacion")
    private String tokenVerification;

    @Column(name = "fecha_fin_verificacion")
    private LocalDateTime verificationEndDate;

    @Column(name = "verificacion_activa")
    private boolean activeVerification;
}
