package revista_backend.repositories.cost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.cost.SuggestedCost;

@Repository
public interface SuggestedCostRepository extends JpaRepository<SuggestedCost, Integer> {

    boolean existsByDays(int days);
}