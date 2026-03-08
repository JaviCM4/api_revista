package revista_backend.repositories.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.advertisement.AdBlock;
import revista_backend.models.magazine.Magazine;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AdBlockRepository extends JpaRepository<AdBlock, Integer> {

    List<AdBlock> findAllByAdLockStatus_Id(Integer idAdLockStatus);

    List<AdBlock> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<AdBlock> findByMagazine(Magazine magazine);

    List<AdBlock> findByStartDateBetweenAndMagazine_User_Id(LocalDate startDate, LocalDate endDate, Integer idUser);

    List<AdBlock> findByStartDateBetweenAndMagazine(LocalDate startDate, LocalDate endDate, Magazine magazine);
}
