package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.Edition;
import revista_backend.models.magazine.Magazine;

import java.util.List;

@Repository
public interface EditionRepository extends JpaRepository<Edition, Integer> {

    List<Edition> findByMagazine_Id(Integer idMagazine);
}
