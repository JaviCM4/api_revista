package revista_backend.repositories.types;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.types.UserType;

@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Integer> {
}
