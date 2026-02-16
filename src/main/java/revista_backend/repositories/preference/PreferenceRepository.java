package revista_backend.repositories.preference;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.preference.Preference;

@Repository
public interface PreferenceRepository extends JpaRepository<Preference, Integer> {

}
