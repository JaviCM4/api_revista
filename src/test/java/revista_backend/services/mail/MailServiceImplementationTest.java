package revista_backend.services.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MailServiceImplementationTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailServiceImplementation service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "from", "noreply@revista.com");
    }

    @Test
    void testSendTokenEmailForLogin() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        service.sendTokenEmail("user@test.com", "TOKEN123", "login");

        verify(mailSender).send(captor.capture());
        SimpleMailMessage message = captor.getValue();

        assertAll(
                () -> assertEquals("noreply@revista.com", message.getFrom()),
                () -> assertEquals("user@test.com", message.getTo()[0]),
                () -> assertEquals("Verificacion de inicio de sesion", normalizeAccents(message.getSubject())),
                () -> assertEquals("Tu token para iniciar sesion es: TOKEN123", normalizeAccents(message.getText()))
        );
    }

    @Test
    void testSendTokenEmailForRecover() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        service.sendTokenEmail("user@test.com", "RECOVER456", "recover");

        verify(mailSender).send(captor.capture());
        SimpleMailMessage message = captor.getValue();

        assertAll(
                () -> assertEquals("Recuperacion de contrasena", normalizeAccents(message.getSubject())),
                () -> assertEquals("Tu token para recuperar contrasena es: RECOVER456", normalizeAccents(message.getText()))
        );
    }

    @Test
    void testSendTokenEmailDefaultPurpose() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        service.sendTokenEmail("user@test.com", "GEN789", "firstLogin");

        verify(mailSender).send(captor.capture());
        SimpleMailMessage message = captor.getValue();

        assertAll(
                () -> assertEquals("Token", message.getSubject()),
                () -> assertEquals("Tu token es: GEN789", message.getText())
        );
    }

    private String normalizeAccents(String input) {
        return input
                .replace("ó", "o")
                .replace("ñ", "n")
                .replace("í", "i")
                .replace("á", "a")
                .replace("é", "e")
                .replace("ú", "u")
                .replace("Ó", "O")
                .replace("Ñ", "N")
                .replace("Í", "I")
                .replace("Á", "A")
                .replace("É", "E")
                .replace("Ú", "U");
    }
}
