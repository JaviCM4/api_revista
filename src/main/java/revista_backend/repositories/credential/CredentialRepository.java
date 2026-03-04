package revista_backend.repositories.credential;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.credential.Credential;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Integer> {

    boolean existsByUsername(String username);

    Optional<Credential> findByUsername(String username);

    Optional<Credential> findByUser_Id(Integer idUser);

    Optional<Credential> findByTokenRecovery(String token);

    Optional<Credential> findBytokenVerification(String token);
}
