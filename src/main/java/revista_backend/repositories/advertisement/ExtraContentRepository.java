package revista_backend.repositories.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.advertisement.ExtraContent;

import java.util.List;

@Repository
public interface ExtraContentRepository extends JpaRepository<ExtraContent, Integer> {

    List<ExtraContent>  findByAdvertisement_Id(Integer idAdvertisement);
}
