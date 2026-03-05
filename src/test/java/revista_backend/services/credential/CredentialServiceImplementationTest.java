package revista_backend.services.credential;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.credential.CredentialResquest;
import revista_backend.dto.credential.RecoverPasswordRequest;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.contact.Contact;
import revista_backend.models.credential.Credential;
import revista_backend.models.user.User;
import revista_backend.repositories.contact.ContactRepository;
import revista_backend.repositories.credential.CredentialRepository;
import revista_backend.repositories.user.UserRepository;
import revista_backend.security.JwtUtil;
import revista_backend.services.mail.MailService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CredentialServiceImplementationTest {

    @Mock
    private CredentialRepository credentialRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    @InjectMocks
    private CredentialServiceImplementation service;

    private Credential credential;
    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(5);
        credential = new Credential();
        credential.setId(10);
        credential.setUsername("u1");
        credential.setPassword("encoded");
        credential.setUser(user);
    }

    @Test
    void loginReturnsJwtWhenVerificationInactive() throws Exception {
        CredentialResquest req = new CredentialResquest("u1", "pwd");
        when(credentialRepository.findByUsername("u1")).thenReturn(Optional.of(credential));
        when(passwordEncoder.matches("pwd", "encoded")).thenReturn(true);
        credential.setActiveVerification(false);
        when(jwtUtil.generateToken("u1", 10)).thenReturn("jwt");

        var res = service.getLoginResponse(req);
        assertEquals("jwt", res.getToken());
        assertEquals(10, res.getIdRole());
        verify(mailService, never()).sendTokenEmail(any(), any(), any());
    }

    @Test
    void loginWithActiveVerificationSendsEmail() throws Exception {
        CredentialResquest req = new CredentialResquest("u1", "pwd");
        when(credentialRepository.findByUsername("u1")).thenReturn(Optional.of(credential));
        when(passwordEncoder.matches("pwd", "encoded")).thenReturn(true);
        credential.setActiveVerification(true);

        Contact contact = new Contact();
        contact.setDetail("test@example.com");
        when(contactRepository.findByUser_IdAndContactType_Id(5, 2)).thenReturn(Optional.of(contact));
        when(credentialRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.getLoginResponse(req);

        verify(mailService).sendTokenEmail(eq("test@example.com"), anyString(), eq("login"));
        assertNotNull(credential.getTokenVerification());
        assertNotNull(credential.getVerificationEndDate());
    }

    @Test
    void toggleActiveVerification() throws Exception {
        when(userRepository.findById(5)).thenReturn(Optional.of(user));
        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.of(credential));
        credential.setActiveVerification(false);
        service.updateActiveVerification(5);
        assertTrue(credential.isActiveVerification());
        service.updateActiveVerification(5);
        assertFalse(credential.isActiveVerification());
    }

    @Test
    void recoverPasswordGeneratesTokenAndEmails() throws Exception {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setDetail("mail@d.com");
        when(contactRepository.findByDetail("mail@d.com")).thenReturn(Optional.of(contact));
        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.of(credential));
        when(credentialRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.recoverPassword("mail@d.com");
        assertNotNull(credential.getTokenRecovery());
        assertNotNull(credential.getRecoveryEndDate());
        verify(mailService).sendTokenEmail(eq("mail@d.com"), anyString(), eq("recover"));
    }

    @Test
    void verifyPasswordSucceedsAndClearsToken() throws Exception {
        String token = "tok";
        credential.setTokenRecovery(token);
        credential.setRecoveryEndDate(LocalDateTime.now().plusMinutes(2));
        when(credentialRepository.findByTokenRecovery(token)).thenReturn(Optional.of(credential));
        when(passwordEncoder.encode("new")).thenReturn("newEncoded");
        when(credentialRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        service.verifyPassword(new RecoverPasswordRequest(token, "new"));
        assertNull(credential.getTokenRecovery());
        assertNull(credential.getRecoveryEndDate());
        assertEquals("newEncoded", credential.getPassword());
    }

    @Test
    void verifyPasswordThrowsWhenExpired() {
        String token = "tok";
        credential.setTokenRecovery(token);
        credential.setRecoveryEndDate(LocalDateTime.now().minusMinutes(1));
        when(credentialRepository.findByTokenRecovery(token)).thenReturn(Optional.of(credential));

        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.verifyPassword(new RecoverPasswordRequest(token, "x")));
        assertEquals("Expired Token", ex.getMessage());
    }

    @Test
    void verifyLoginTokenReturnsJwt() throws Exception {
        String token = "vtok";
        credential.setTokenVerification(token);
        credential.setVerificationEndDate(LocalDateTime.now().plusMinutes(5));
        when(credentialRepository.findBytokenVerification(token)).thenReturn(Optional.of(credential));
        when(jwtUtil.generateToken("u1", 10)).thenReturn("finalJwt");
        when(credentialRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        var res = service.verifyLoginToken(token);
        assertEquals("finalJwt", res.getToken());
        assertNull(credential.getTokenVerification());
        assertNull(credential.getVerificationEndDate());
    }

    @Test
    void verifyLoginTokenThrowsOnExpired() {
        String token = "vtok";
        credential.setTokenVerification(token);
        credential.setVerificationEndDate(LocalDateTime.now().minusSeconds(10));
        when(credentialRepository.findBytokenVerification(token)).thenReturn(Optional.of(credential));

        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.verifyLoginToken(token));
        assertEquals("Expired Token", ex.getMessage());
    }
}
