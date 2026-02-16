package revista_backend.repositories.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.advertisement.AdBlock;

@Repository
public interface AdBlockRepository extends JpaRepository<AdBlock, Integer> {

}
