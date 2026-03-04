package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.MagazineInteraction;

import java.util.List;

@Repository
public interface MagazineInteractionRepository extends JpaRepository<MagazineInteraction, Integer> {

    MagazineInteraction findByMagazine_IdAndUser_Id(int magazineId, int userId);

    List<MagazineInteraction> findByMagazine_Id(int magazineId);

    Integer countByMagazine_IdAndLikedTrue(Integer idMagazine);
}
