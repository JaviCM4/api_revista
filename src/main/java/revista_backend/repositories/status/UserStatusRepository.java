package revista_backend.repositories.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.status.UserStatus;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
}
