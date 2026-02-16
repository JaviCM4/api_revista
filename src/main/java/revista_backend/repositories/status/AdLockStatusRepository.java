package revista_backend.repositories.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.status.AdLockStatus;

@Repository
public interface AdLockStatusRepository extends JpaRepository<AdLockStatus, Integer> {
}
