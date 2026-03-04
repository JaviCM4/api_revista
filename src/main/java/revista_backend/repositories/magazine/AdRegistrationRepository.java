package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import revista_backend.models.magazine.AdRegistration;

public interface AdRegistrationRepository extends JpaRepository<AdRegistration, Integer> {
}
