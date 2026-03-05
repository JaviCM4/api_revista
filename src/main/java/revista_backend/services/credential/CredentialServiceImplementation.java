package revista_backend.services.credential;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import revista_backend.dto.credential.CredentialResquest;
import revista_backend.dto.credential.JwtResponse;
import revista_backend.dto.credential.RecoverPasswordRequest;
import revista_backend.exceptions.ConflictException;
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

@Service
public class CredentialServiceImplementation implements CredentialService {

    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final MailService mailService;

    @Autowired
    public CredentialServiceImplementation(CredentialRepository credentialRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserRepository userRepository, ContactRepository contactRepository, MailService mailService) {
        this.credentialRepository = credentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
        this.mailService = mailService;
    }

    @Override
    public void create(User user, String username, String password) throws ConflictException {

        if (credentialRepository.existsByUsername(username)) {
            throw new ConflictException("Username '" + username + "' it's already registered. Please use another one.");
        }

        Credential credential = new Credential();
        credential.setUser(user);
        credential.setUsername(username);
        credential.setPassword(passwordEncoder.encode(password));

        credentialRepository.save(credential);
    }

    @Override
    public JwtResponse getLoginResponse(CredentialResquest dto)
            throws ResourceNotFoundException, ValidationException {
        String token = "";

        Credential credential = credentialRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), credential.getPassword())) {
            throw new ValidationException("Invalid credentials");
        }

        if (!credential.isActiveVerification()) {
            token = jwtUtil.generateToken(dto.getUsername(), credential.getId());
        } else {
            String tempToken = java.util.UUID.randomUUID().toString();
            credential.setTokenVerification(tempToken);
            credential.setVerificationEndDate(LocalDateTime.now().plusMinutes(5));
            credentialRepository.save(credential);

            Contact emailContact = contactRepository.findByUser_IdAndContactType_Id(credential.getUser().getId(), 1)
                    .orElseThrow(() -> new ResourceNotFoundException("Email contact not found"));
            mailService.sendTokenEmail(emailContact.getDetail(), tempToken, "login");
        }

        return new JwtResponse(token, credential.getUser().getUserType().getId(), credential.getUsername());
    }

    @Override
    public void updateActiveVerification(Integer idUser)
            throws ResourceNotFoundException {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Credential credential = credentialRepository.findByUser_Id(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Credential not found"));

        if (credential.isActiveVerification()) {
            credential.setActiveVerification(false);
        } else {
            credential.setActiveVerification(true);
        }
        credentialRepository.save(credential);
    }

    @Override
    public void recoverPassword(String email) throws ResourceNotFoundException {
        Contact contact = contactRepository.findByDetail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

        Credential credential = credentialRepository.findByUser_Id(contact.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Credential not found"));

        String tokenRec = java.util.UUID.randomUUID().toString();
        credential.setTokenRecovery(tokenRec);
        credential.setRecoveryEndDate(LocalDateTime.now().plusHours(1));
        credentialRepository.save(credential);

        mailService.sendTokenEmail(email, tokenRec, "recover");
    }

    @Override
    public void verifyPassword(RecoverPasswordRequest dto)
            throws ResourceNotFoundException, ValidationException {
        Credential credential = credentialRepository.findByTokenRecovery(dto.getTokenRecover())
                .orElseThrow(() -> new ResourceNotFoundException("Credential not found"));

        if (credential.getRecoveryEndDate() != null && credential.getRecoveryEndDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Expired Token");
        }

        credential.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        credential.setTokenRecovery(null);
        credential.setRecoveryEndDate(null);
        credentialRepository.save(credential);
    }

    @Override
    public JwtResponse verifyLoginToken(String tokenVerificacion) throws ResourceNotFoundException, ValidationException {
        Credential credential = credentialRepository.findBytokenVerification(tokenVerificacion)
                .orElseThrow(() -> new ResourceNotFoundException("Credential not found"));

        if (credential.getVerificationEndDate() != null && credential.getVerificationEndDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Expired Token");
        }
        credential.setTokenVerification(null);
        credential.setVerificationEndDate(null);
        credentialRepository.save(credential);
        String tokenResp = jwtUtil.generateToken(credential.getUsername(), credential.getId());
        return new JwtResponse(tokenResp, credential.getUser().getUserType().getId(), credential.getUsername());
    }
}


