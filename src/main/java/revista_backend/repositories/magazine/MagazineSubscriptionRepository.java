package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.MagazineSubscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface MagazineSubscriptionRepository extends JpaRepository<MagazineSubscription, Integer> {

    boolean existsByMagazine_IdAndUser_Id(Integer magazineId, Integer userId);

    Optional<MagazineSubscription> findByMagazine_IdAndUser_Id(Integer magazineId, Integer userId);

    List<MagazineSubscription> findByUser_Id(int userId);
}
