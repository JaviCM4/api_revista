package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.Magazine;

import java.util.List;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Integer> {

    List<Magazine> findByUser_Id(Integer user);

    @Query("""
        SELECT DISTINCT m
        FROM Magazine m
        JOIN MagazineCategory mc ON mc.magazine.id = m.id
        WHERE mc.magazineCategoryType.id = :idCategory
    """)
    List<Magazine> findByCategory_Id(Integer idCategory);

    List<Magazine> findByActiveMagazine(Boolean activeMagazine);
}
