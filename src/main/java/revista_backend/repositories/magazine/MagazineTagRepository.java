package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazineTag;

import java.util.List;

@Repository
public interface MagazineTagRepository extends JpaRepository<MagazineTag, Integer> {

    boolean existsByMagazine_IdAndDetailIgnoreCase(Integer magazineId, String detail);

    List<MagazineTag> findByMagazine_Id(Integer magazineId);

    List<MagazineTag> findByDetailIgnoreCase(String tag);
}
