package revista_backend.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.categories.MagazineCategory;

import java.util.List;

@Repository
public interface MagazineCategoryRepository extends JpaRepository<MagazineCategory, Integer> {

    boolean existsByMagazine_IdAndMagazineCategoryType_Id(int MagazineId, int CategoryTypeId);

    List<MagazineCategory> findByMagazine_Id(int MagazineId);

}
