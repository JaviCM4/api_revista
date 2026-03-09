package revista_backend.services.orchestrator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.user.UserCreateAdminRequest;
import revista_backend.dto.user.UserCreateRequest;
import revista_backend.dto.user.UserCreateResponse;
import revista_backend.models.user.User;
import revista_backend.services.contact.ContactServiceImplementation;
import revista_backend.services.credential.CredentialServiceImplementation;
import revista_backend.services.user.UserServiceImplementation;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrchestratorUserCredentialServiceImplementationTest {

    private static final int USER_ID = 7;
    private static final String USERNAME = "john_user";
    private static final String PASSWORD = "Pass123!";
    private static final String EMAIL = "john@example.com";
    private static final Integer PHONE = 55667788;

    @Mock
    private UserServiceImplementation userServiceImplementation;

    @Mock
    private CredentialServiceImplementation credentialServiceImplementation;

    @Mock
    private ContactServiceImplementation contactServiceImplementation;

    @InjectMocks
    private OrchestratorUserCredentialServiceImplementation service;

    @Test
    void testCreateUserWithCredentialSuccess() throws Exception {
        UserCreateRequest dto = createUserRequest();
        User newUser = createUser(USER_ID, "John", "Doe");

        when(userServiceImplementation.create(dto)).thenReturn(newUser);

        UserCreateResponse result = service.createUserWithCredential(dto);

        var inOrder = inOrder(userServiceImplementation, contactServiceImplementation, credentialServiceImplementation);
        inOrder.verify(userServiceImplementation).create(dto);
        inOrder.verify(contactServiceImplementation).create(newUser, PHONE, EMAIL);
        inOrder.verify(credentialServiceImplementation).create(newUser, USERNAME, PASSWORD);

        assertAll(
                () -> assertEquals(USER_ID, result.getId()),
                () -> assertEquals("John", result.getNames()),
                () -> assertEquals("Doe", result.getLastnames())
        );
    }

    @Test
    void testCreateUserWithCredentialAdminSuccess() throws Exception {
        UserCreateAdminRequest dto = createUserAdminRequest();
        User newUser = createUser(USER_ID, "Jane", "Admin");

        when(userServiceImplementation.createAdmin(dto)).thenReturn(newUser);

        UserCreateResponse result = service.createUserWithCredentialAdmin(dto);

        var inOrder = inOrder(userServiceImplementation, contactServiceImplementation, credentialServiceImplementation);
        inOrder.verify(userServiceImplementation).createAdmin(dto);
        inOrder.verify(contactServiceImplementation).create(newUser, PHONE, EMAIL);
        inOrder.verify(credentialServiceImplementation).createAdmin(newUser, USERNAME, EMAIL);

        assertAll(
                () -> assertEquals(USER_ID, result.getId()),
                () -> assertEquals("Jane", result.getNames()),
                () -> assertEquals("Admin", result.getLastnames())
        );
    }

    private UserCreateRequest createUserRequest() {
        return new UserCreateRequest(
                1,
                1,
                1,
                USERNAME,
                "John",
                "Doe",
                LocalDate.of(1995, 1, 15),
                "photo.png",
                "desc",
                PHONE,
                EMAIL,
                PASSWORD
        );
    }

    private UserCreateAdminRequest createUserAdminRequest() {
        return new UserCreateAdminRequest(
                1,
                1,
                1,
                1,
                USERNAME,
                "Jane",
                "Admin",
                LocalDate.of(1990, 5, 20),
                "photo-admin.png",
                "admin desc",
                PHONE,
                EMAIL
        );
    }

    private User createUser(Integer id, String names, String lastNames) {
        User user = new User();
        user.setId(id);
        user.setNames(names);
        user.setLastNames(lastNames);
        return user;
    }
}
