package revista_backend.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.categories.MagazineCategory;

@Repository
public interface MagazineCategoryRepository extends JpaRepository<MagazineCategory, Integer> {

}
