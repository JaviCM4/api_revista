package revista_backend.repositories.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.categories.PreferenceCategory;

@Repository
public interface PreferenceCategoryRepository extends JpaRepository<PreferenceCategory, Integer> {

}
