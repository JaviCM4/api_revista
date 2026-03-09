package revista_backend.services.credential;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revista_backend.dto.credential.CredentialResquest;
import revista_backend.dto.credential.FirstLoginRequest;
import revista_backend.dto.credential.RecoverPasswordRequest;
import revista_backend.exceptions.ConflictException;
import revista_backend.exceptions.ResourceNotFoundException;
import revista_backend.exceptions.ValidationException;
import revista_backend.models.contact.Contact;
import revista_backend.models.credential.Credential;
import revista_backend.models.types.UserType;
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
        UserType userType = new UserType();
        userType.setId(10);
        user.setUserType(userType);

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
        when(contactRepository.findByUser_IdAndContactType_Id(5, 1)).thenReturn(Optional.of(contact));
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
    void verifyPasswordSucceedsWhenRecoveryEndDateIsNull() throws Exception {
        String token = "tok-null-date";
        credential.setTokenRecovery(token);
        credential.setRecoveryEndDate(null);
        when(credentialRepository.findByTokenRecovery(token)).thenReturn(Optional.of(credential));
        when(passwordEncoder.encode("new")).thenReturn("newEncoded");

        service.verifyPassword(new RecoverPasswordRequest(token, "new"));

        assertEquals("newEncoded", credential.getPassword());
        assertNull(credential.getTokenRecovery());
        assertNull(credential.getRecoveryEndDate());
        verify(credentialRepository).save(credential);
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

    @Test
    void verifyLoginTokenSucceedsWhenVerificationEndDateIsNull() throws Exception {
        String token = "vtok-null-date";
        credential.setTokenVerification(token);
        credential.setVerificationEndDate(null);
        when(credentialRepository.findBytokenVerification(token)).thenReturn(Optional.of(credential));
        when(jwtUtil.generateToken("u1", 10)).thenReturn("jwt-null-date");

        var response = service.verifyLoginToken(token);

        assertEquals("jwt-null-date", response.getToken());
        assertNull(credential.getTokenVerification());
        assertNull(credential.getVerificationEndDate());
        verify(credentialRepository).save(credential);
    }

    @Test
    void createStoresEncodedPassword() throws Exception {
        User localUser = new User();
        localUser.setId(9);
        when(credentialRepository.existsByUsername("newUser")).thenReturn(false);
        when(passwordEncoder.encode("pwd")).thenReturn("encodedPwd");

        service.create(localUser, "newUser", "pwd");

        verify(passwordEncoder).encode("pwd");
        verify(credentialRepository).save(argThat(saved ->
                saved.getUser().equals(localUser)
                        && saved.getUsername().equals("newUser")
                        && saved.getPassword().equals("encodedPwd")
        ));
    }

    @Test
    void createThrowsConflictWhenUsernameAlreadyExists() {
        when(credentialRepository.existsByUsername("u1")).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class,
                () -> service.create(user, "u1", "pwd"));

        assertEquals("Username 'u1' it's already registered. Please use another one.", ex.getMessage());
        verify(credentialRepository, never()).save(any(Credential.class));
    }

    @Test
    void createAdminGeneratesTokenAndSendsEmail() throws Exception {
        when(credentialRepository.existsByUsername("admin1")).thenReturn(false);

        service.createAdmin(user, "admin1", "admin@test.com");

        verify(mailService).sendTokenEmail(eq("admin@test.com"), anyString(), eq("firstLogin"));
        verify(credentialRepository).save(argThat(saved ->
                saved.getUsername().equals("admin1")
                        && saved.getPassword() == null
                        && saved.getTokenVerification() != null
                        && saved.getVerificationEndDate() != null
        ));
    }

    @Test
    void createAdminThrowsConflictWhenUsernameAlreadyExists() {
        when(credentialRepository.existsByUsername("admin1")).thenReturn(true);

        ConflictException ex = assertThrows(ConflictException.class,
                () -> service.createAdmin(user, "admin1", "admin@test.com"));

        assertEquals("Username 'admin1' it's already registered. Please use another one.", ex.getMessage());
        verify(mailService, never()).sendTokenEmail(anyString(), anyString(), anyString());
        verify(credentialRepository, never()).save(any(Credential.class));
    }

    @Test
    void loginThrowsValidationWhenPasswordIsInvalid() {
        CredentialResquest req = new CredentialResquest("u1", "bad");
        when(credentialRepository.findByUsername("u1")).thenReturn(Optional.of(credential));
        when(passwordEncoder.matches("bad", "encoded")).thenReturn(false);

        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.getLoginResponse(req));

        assertEquals("Invalid credentials", ex.getMessage());
    }

    @Test
    void loginThrowsWhenUserNotFound() {
        CredentialResquest req = new CredentialResquest("ghost", "pwd");
        when(credentialRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getLoginResponse(req));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void loginWithActiveVerificationThrowsWhenEmailContactNotFound() {
        CredentialResquest req = new CredentialResquest("u1", "pwd");
        credential.setActiveVerification(true);
        when(credentialRepository.findByUsername("u1")).thenReturn(Optional.of(credential));
        when(passwordEncoder.matches("pwd", "encoded")).thenReturn(true);
        when(contactRepository.findByUser_IdAndContactType_Id(5, 1)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.getLoginResponse(req));

        assertEquals("Email contact not found", ex.getMessage());
        verify(mailService, never()).sendTokenEmail(anyString(), anyString(), anyString());
    }

    @Test
    void verifyFirstLoginSucceeds() throws Exception {
        credential.setTokenVerification("first-token");
        credential.setVerificationEndDate(LocalDateTime.now().plusMinutes(2));
        credential.setPassword(null);

        when(credentialRepository.findBytokenVerification("first-token")).thenReturn(Optional.of(credential));
        when(passwordEncoder.encode("pass1")).thenReturn("passEncoded");

        service.verifyFirstLogin(new FirstLoginRequest("u1", "pass1", "first-token"));

        assertEquals("passEncoded", credential.getPassword());
        assertNull(credential.getTokenVerification());
        assertNull(credential.getVerificationEndDate());
        verify(credentialRepository).save(credential);
    }

    @Test
    void updateActiveVerificationThrowsWhenUserNotFound() {
        when(userRepository.findById(5)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.updateActiveVerification(5));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void updateActiveVerificationThrowsWhenCredentialNotFound() {
        when(userRepository.findById(5)).thenReturn(Optional.of(user));
        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.updateActiveVerification(5));

        assertEquals("Credential not found", ex.getMessage());
    }

    @Test
    void recoverPasswordThrowsWhenContactNotFound() {
        when(contactRepository.findByDetail("none@mail.com")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.recoverPassword("none@mail.com"));

        assertEquals("Contact not found", ex.getMessage());
    }

    @Test
    void recoverPasswordThrowsWhenCredentialNotFound() {
        Contact contact = new Contact();
        contact.setUser(user);
        contact.setDetail("mail@d.com");
        when(contactRepository.findByDetail("mail@d.com")).thenReturn(Optional.of(contact));
        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.recoverPassword("mail@d.com"));

        assertEquals("Credential not found", ex.getMessage());
        verify(mailService, never()).sendTokenEmail(anyString(), anyString(), anyString());
    }

    @Test
    void verifyPasswordThrowsWhenCredentialNotFound() {
        when(credentialRepository.findByTokenRecovery("bad-token")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.verifyPassword(new RecoverPasswordRequest("bad-token", "new")));

        assertEquals("Credential not found", ex.getMessage());
    }

    @Test
    void verifyLoginTokenThrowsWhenCredentialNotFound() {
        when(credentialRepository.findBytokenVerification("bad-token")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.verifyLoginToken("bad-token"));

        assertEquals("Credential not found", ex.getMessage());
    }

    @Test
    void verifyFirstLoginThrowsWhenTokenExpired() {
        credential.setTokenVerification("expired-first-token");
        credential.setVerificationEndDate(LocalDateTime.now().minusMinutes(1));
        when(credentialRepository.findBytokenVerification("expired-first-token")).thenReturn(Optional.of(credential));

        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.verifyFirstLogin(new FirstLoginRequest("u1", "pass1", "expired-first-token")));

        assertEquals("Expired Token", ex.getMessage());
        verify(credentialRepository, never()).save(any(Credential.class));
    }

    @Test
    void verifyFirstLoginSucceedsWhenVerificationEndDateIsNull() throws Exception {
        credential.setTokenVerification("first-null-date");
        credential.setVerificationEndDate(null);
        credential.setPassword(null);
        when(credentialRepository.findBytokenVerification("first-null-date")).thenReturn(Optional.of(credential));
        when(passwordEncoder.encode("pass1")).thenReturn("passEncodedNullDate");

        service.verifyFirstLogin(new FirstLoginRequest("u1", "pass1", "first-null-date"));

        assertEquals("passEncodedNullDate", credential.getPassword());
        assertNull(credential.getTokenVerification());
        assertNull(credential.getVerificationEndDate());
        verify(credentialRepository).save(credential);
    }

    @Test
    void verifyFirstLoginThrowsWhenNotFirstLogin() {
        credential.setTokenVerification("token-used");
        credential.setVerificationEndDate(LocalDateTime.now().plusMinutes(2));
        credential.setPassword("already");
        when(credentialRepository.findBytokenVerification("token-used")).thenReturn(Optional.of(credential));

        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.verifyFirstLogin(new FirstLoginRequest("u1", "pass1", "token-used")));

        assertEquals("This is not your first login", ex.getMessage());
        verify(credentialRepository, never()).save(any(Credential.class));
    }

    @Test
    void verifyFirstLoginThrowsWhenCredentialNotFound() {
        when(credentialRepository.findBytokenVerification("missing-first-token")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.verifyFirstLogin(new FirstLoginRequest("u1", "pass1", "missing-first-token")));

        assertEquals("Credential not found", ex.getMessage());
    }

    @Test
    void verifyFirstLoginThrowsWhenUsernameDoesNotMatch() {
        credential.setTokenVerification("first-token");
        credential.setVerificationEndDate(LocalDateTime.now().plusMinutes(2));
        credential.setPassword(null);

        when(credentialRepository.findBytokenVerification("first-token")).thenReturn(Optional.of(credential));

        ValidationException ex = assertThrows(ValidationException.class,
                () -> service.verifyFirstLogin(new FirstLoginRequest("other", "pass1", "first-token")));

        assertEquals("Incorrect username", ex.getMessage());
        verify(credentialRepository, never()).save(any(Credential.class));
    }

    @Test
    void sendLoginTokenSucceeds() throws Exception {
        credential.setPassword("");
        Contact contact = new Contact();
        contact.setDetail("token@test.com");

        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.of(credential));
        when(contactRepository.findByUser_IdAndContactType_Id(5, 1)).thenReturn(Optional.of(contact));

        service.sendLoginToken(5);

        verify(credentialRepository).save(credential);
        verify(mailService).sendTokenEmail(eq("token@test.com"), anyString(), eq("firstLogin"));
        assertNotNull(credential.getTokenVerification());
        assertNotNull(credential.getVerificationEndDate());
    }

    @Test
    void sendLoginTokenThrowsConflictWhenUserAlreadyHasPassword() {
        credential.setPassword("already-set");
        Contact contact = new Contact();
        contact.setDetail("token@test.com");

        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.of(credential));
        when(contactRepository.findByUser_IdAndContactType_Id(5, 1)).thenReturn(Optional.of(contact));

        ConflictException ex = assertThrows(ConflictException.class,
                () -> service.sendLoginToken(5));

        assertEquals("This user has already logged in for the first time", ex.getMessage());
        verify(credentialRepository, never()).save(any(Credential.class));
        verify(mailService, never()).sendTokenEmail(anyString(), anyString(), anyString());
    }

    @Test
    void sendLoginTokenThrowsWhenCredentialNotFound() {
        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.sendLoginToken(5));

        assertEquals("Credential not found", ex.getMessage());
        verify(contactRepository, never()).findByUser_IdAndContactType_Id(anyInt(), anyInt());
    }

    @Test
    void sendLoginTokenThrowsWhenContactNotFound() {
        credential.setPassword("");
        when(credentialRepository.findByUser_Id(5)).thenReturn(Optional.of(credential));
        when(contactRepository.findByUser_IdAndContactType_Id(5, 1)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.sendLoginToken(5));

        assertEquals("Credential not found", ex.getMessage());
        verify(credentialRepository, never()).save(any(Credential.class));
        verify(mailService, never()).sendTokenEmail(anyString(), anyString(), anyString());
    }
}
