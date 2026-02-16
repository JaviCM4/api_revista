package revista_backend.repositories.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.location.Municipality;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Integer> {

}
