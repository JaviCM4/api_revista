package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.InteractionLike;

import java.util.List;

@Repository
public interface MagazineInteractionRepository extends JpaRepository<InteractionLike, Integer> {

    InteractionLike findByMagazine_IdAndUser_Id(int magazineId, int userId);

    List<InteractionLike> findByMagazine_Id(int magazineId);

    Integer countByMagazine_IdAndLikedTrue(Integer idMagazine);
}
