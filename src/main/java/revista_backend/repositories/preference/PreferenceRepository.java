package revista_backend.repositories.preference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.preference.Preference;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Integer> {

    boolean existsByUser_IdAndPreferenceCategory_Id(int user_id, int preference_category_id);

    List<Preference> findByUser_Id(int user_id);
}
