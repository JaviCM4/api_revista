package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.InteractionLike;

import java.util.List;

@Repository
public interface InteractionLikeRepository extends JpaRepository<InteractionLike, Integer> {

    InteractionLike findByMagazine_IdAndUser_Id(int magazineId, int userId);

    Integer countByMagazine_IdAndLikedTrue(Integer idMagazine);
}
