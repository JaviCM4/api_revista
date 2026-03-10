package revista_backend.services.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImplementation implements MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailServiceImplementation.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailServiceImplementation(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
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

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            mailSender.send(message);
            logger.info("Email enviado a: {}", to);
        } catch (Exception e) {
            logger.error("Error al enviar email a {}: {}", to, e.getMessage());
        }
    }
}