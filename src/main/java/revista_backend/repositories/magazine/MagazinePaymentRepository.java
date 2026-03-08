package revista_backend.repositories.magazine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.magazine.Magazine;
import revista_backend.models.magazine.MagazinePayment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MagazinePaymentRepository extends JpaRepository<MagazinePayment, Integer> {

    List<MagazinePayment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    List<MagazinePayment> findByMagazine(Magazine magazine);

    List<MagazinePayment> findByPaymentDateBetweenAndMagazine(LocalDate startDate, LocalDate endDate, Magazine magazine);
}
