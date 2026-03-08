package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.InteractionComment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InteractionCommentRepository extends JpaRepository<InteractionComment, Integer> {

    InteractionComment findByMagazine_IdAndUser_Id(int magazineId, int userId);

    List<InteractionComment> findByMagazine_Id(int magazineId);

    List<InteractionComment> findByCommentDateBetweenAndMagazine_User_Id(LocalDate startDate, LocalDate endDate, Integer idUser);

    List<InteractionComment> findByMagazine_User_Id(Integer idUser);

    List<InteractionComment> findByCommentDateBetween(LocalDate startDate, LocalDate endDate);

}
