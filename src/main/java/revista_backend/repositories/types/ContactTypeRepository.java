package revista_backend.repositories.types;

import org.springframework.data.jpa.repository.JpaRepository;
import revista_backend.models.types.ContactType;

import java.util.Optional;

public interface ContactTypeRepository extends JpaRepository<ContactType, Integer> {
}
