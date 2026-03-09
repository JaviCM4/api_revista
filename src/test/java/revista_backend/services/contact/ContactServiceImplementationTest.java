package revista_backend.services.contact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.models.contact.Contact;
import revista_backend.models.types.ContactType;
import revista_backend.models.user.User;
import revista_backend.repositories.contact.ContactRepository;
import revista_backend.repositories.types.ContactTypeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceImplementationTest {

    private static final String EMAIL = "mail@test.com";
    private static final Integer PHONE = 55667788;

    @Mock
    private ContactTypeRepository contactTypeRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImplementation service;

    @Test
    void testCreateSuccess() throws ResourceNotFoundException, ConflictException {
        User user = createUser(1);
        ContactType phoneType = createContactType(1, "Telefono");
        ContactType emailType = createContactType(2, "Email");

        when(contactTypeRepository.findById(1)).thenReturn(Optional.of(phoneType));
        when(contactTypeRepository.findById(2)).thenReturn(Optional.of(emailType));
        when(contactRepository.existsByDetail(EMAIL)).thenReturn(false);

        ArgumentCaptor<Contact> captor = ArgumentCaptor.forClass(Contact.class);

        service.create(user, PHONE, EMAIL);

        verify(contactTypeRepository).findById(1);
        verify(contactTypeRepository).findById(2);
        verify(contactRepository).existsByDetail(EMAIL);
        verify(contactRepository, times(2)).save(captor.capture());

        Contact savedPhone = captor.getAllValues().get(0);
        Contact savedEmail = captor.getAllValues().get(1);

        assertAll(
                () -> assertEquals(user, savedPhone.getUser()),
                () -> assertEquals("55667788", savedPhone.getDetail()),
                () -> assertEquals(phoneType, savedPhone.getContactType()),
                () -> assertEquals(EMAIL, savedEmail.getDetail()),
                () -> assertEquals(emailType, savedEmail.getContactType())
        );
    }

    @Test
    void testCreateWhenEmailAlreadyExists() throws ResourceNotFoundException {
        User user = createUser(1);
        ContactType phoneType = createContactType(1, "Telefono");

        when(contactTypeRepository.findById(1)).thenReturn(Optional.of(phoneType));
        when(contactRepository.existsByDetail(EMAIL)).thenReturn(true);

        ConflictException exception = assertThrows(
                ConflictException.class,
                () -> service.create(user, PHONE, EMAIL)
        );

        assertEquals("Email address used by another user", exception.getMessage());

        verify(contactTypeRepository).findById(1);
        verify(contactRepository).existsByDetail(EMAIL);
        verify(contactTypeRepository, never()).findById(2);
        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testCreateWhenPhoneTypeNotFound() {
        User user = createUser(1);

        when(contactTypeRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> service.create(user, PHONE, EMAIL)
        );

        assertEquals("Contact Type not found", exception.getMessage());

        verify(contactTypeRepository).findById(1);
        verify(contactRepository, never()).existsByDetail(EMAIL);
        verify(contactRepository, never()).save(any(Contact.class));
    }

    private User createUser(Integer id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    private ContactType createContactType(Integer id, String name) {
        ContactType contactType = new ContactType();
        contactType.setId(id);
        contactType.setName(name);
        return contactType;
    }
}
