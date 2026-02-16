package revista_backend.repositories.credential;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.credential.Credential;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Integer> {

}
