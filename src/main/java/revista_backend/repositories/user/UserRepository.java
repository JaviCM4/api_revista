package revista_backend.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
