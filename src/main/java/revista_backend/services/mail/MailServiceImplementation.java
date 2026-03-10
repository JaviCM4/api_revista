package revista_backend.services.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailServiceImplementation implements MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailServiceImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendTokenEmail(String to, String token, String purpose) {
        String subject;
        String text;
        if ("login".equalsIgnoreCase(purpose)) {
            subject = "Verificación de inicio de sesión";
            text = "Tu token para iniciar sesión es: " + token;
        } else if ("recover".equalsIgnoreCase(purpose)) {
            subject = "Recuperación de contraseña";
            text = "Tu token para recuperar contraseña es: " + token;
        } else {
            subject = "Token";
            text = "Tu token es: " + token;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}