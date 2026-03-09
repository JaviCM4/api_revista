package revista_backend.services.contact;

import org.springframework.stereotype.Service;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.contact.Contact;
import revista_backend.models.types.ContactType;
import revista_backend.models.user.User;
import revista_backend.repositories.contact.ContactRepository;
import revista_backend.repositories.types.ContactTypeRepository;

@Service
public class ContactServiceImplementation implements ContactService {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactRepository contactRepository;

    public ContactServiceImplementation(ContactTypeRepository contactTypeRepository, ContactRepository contactRepository) {
        this.contactTypeRepository = contactTypeRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public void create(User user, Integer phone, String email)
            throws ResourceNotFoundException, ConflictException {
        createContact(user, 1, phone.toString());

        if (contactRepository.existsByDetail(email)) {
            throw new ConflictException("Email address used by another user");
        }
        createContact(user, 2, email);
    }

    private void createContact(User user, int contactTypeId, String detail)
            throws ResourceNotFoundException {
        ContactType contactType = contactTypeRepository.findById(contactTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact Type not found"));

        Contact contact = new Contact();
        contact.setUser(user);
        contact.setContactType(contactType);
        contact.setDetail(detail);

        contactRepository.save(contact);
    }

}
