package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.Magazine;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {

}
