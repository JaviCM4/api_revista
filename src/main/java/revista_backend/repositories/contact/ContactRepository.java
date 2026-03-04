package revista_backend.repositories.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import revista_backend.models.contact.Contact;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Optional<Contact> findByDetail(String detail);

    Optional<Contact> findByUser_IdAndContactType_Id(Integer userId, Integer contactTypeId);

    boolean existsByDetail(String email);
}
