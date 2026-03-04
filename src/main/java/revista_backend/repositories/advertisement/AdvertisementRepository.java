package revista_backend.repositories.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import revista_backend.models.advertisement.Advertisement;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

    List<Advertisement> findAllByAdStatus_Id(Integer idAdStatus);

    @Query("""
        SELECT a
        FROM Advertisement a
        WHERE a.adStatus.id = 1
          AND a.id NOT IN (
              SELECT b.advertisement.id
              FROM AdBlock b
              WHERE b.advertisement.id = :idAdvertisement
                AND b.adLockStatus.id = 1
          )
    """)
    List<Advertisement> findAllowedAdsByMagazine(Integer idMagazine);
}
