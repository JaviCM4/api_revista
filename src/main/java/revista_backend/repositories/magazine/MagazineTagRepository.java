package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.MagazineTag;

@Repository
public interface MagazineTagRepository extends JpaRepository<MagazineTag, Integer> {

}
