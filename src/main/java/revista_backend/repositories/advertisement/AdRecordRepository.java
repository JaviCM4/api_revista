package revista_backend.repositories.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.advertisement.AdRecord;

@Repository
public interface AdRecordRepository extends JpaRepository<AdRecord, Integer> {

}
