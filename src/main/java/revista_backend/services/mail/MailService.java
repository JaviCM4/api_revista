package revista_backend.services.mail;

public interface MailService {

    void sendTokenEmail(String to, String token, String purpose);
}